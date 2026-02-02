import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IssueService, BookIssue } from '../../../services/issue.service';

@Component({
  selector: 'app-issue-list',
  templateUrl: './issue-list.component.html',
  styleUrls: ['./issue-list.component.css']
})
export class IssueListComponent implements OnInit {
  issues: BookIssue[] = [];
  showActiveOnly = false;

  constructor(
    private issueService: IssueService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadIssues();
  }

  loadIssues(): void {
    if (this.showActiveOnly) {
      this.issueService.getActiveIssues().subscribe({
        next: (data) => this.issues = data,
        error: (err) => console.error('Error loading issues:', err)
      });
    } else {
      this.issueService.getAllIssues().subscribe({
        next: (data) => this.issues = data,
        error: (err) => console.error('Error loading issues:', err)
      });
    }
  }

  returnBook(id: number): void {
    if (confirm('Are you sure you want to return this book?')) {
      this.issueService.returnBook(id).subscribe({
        next: (issue) => {
          if (issue.fine > 0) {
            alert(`Book returned successfully. Fine: $${issue.fine}`);
          } else {
            alert('Book returned successfully.');
          }
          this.loadIssues();
        },
        error: (err) => alert('Error returning book')
      });
    }
  }

  calculateFine(id: number): void {
    this.issueService.calculateFine(id).subscribe({
      next: (result) => alert(`Current fine: $${result.fine}`),
      error: (err) => console.error('Error calculating fine:', err)
    });
  }

  toggleFilter(): void {
    this.showActiveOnly = !this.showActiveOnly;
    this.loadIssues();
  }
}
