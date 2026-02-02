import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IssueService } from '../../../services/issue.service';
import { MemberService, Member } from '../../../services/member.service';
import { BookService, Book } from '../../../services/book.service';
import { BookCopyService, BookCopy } from '../../../services/book-copy.service';

@Component({
  selector: 'app-issue-book',
  templateUrl: './issue-book.component.html',
  styleUrls: ['./issue-book.component.css']
})
export class IssueBookComponent implements OnInit {
  members: Member[] = [];
  books: Book[] = [];
  availableCopies: BookCopy[] = [];
  
  selectedMemberId?: number;
  selectedBookId?: number;
  selectedCopyId?: number;

  constructor(
    private issueService: IssueService,
    private memberService: MemberService,
    private bookService: BookService,
    private bookCopyService: BookCopyService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadActiveMembers();
    this.loadBooks();
  }

  loadActiveMembers(): void {
    this.memberService.getActiveMembers().subscribe({
      next: (data) => this.members = data,
      error: (err) => console.error('Error loading members:', err)
    });
  }

  loadBooks(): void {
    this.bookService.getAllBooks().subscribe({
      next: (data) => this.books = data,
      error: (err) => console.error('Error loading books:', err)
    });
  }

  onBookSelect(): void {
    if (this.selectedBookId) {
      this.bookCopyService.getCopiesByStatus('AVAILABLE').subscribe({
        next: (allAvailable) => {
          this.availableCopies = allAvailable.filter(c => c.bookId === this.selectedBookId);
        },
        error: (err) => console.error('Error loading copies:', err)
      });
    }
  }

  issueBook(): void {
    if (this.selectedMemberId && this.selectedCopyId) {
      this.issueService.issueBook(this.selectedMemberId, this.selectedCopyId).subscribe({
        next: () => {
          alert('Book issued successfully!');
          this.router.navigate(['/issues']);
        },
        error: (err) => alert('Error issuing book: ' + (err.error?.error || err.message))
      });
    } else {
      alert('Please select both member and book copy');
    }
  }

  cancel(): void {
    this.router.navigate(['/issues']);
  }
}
