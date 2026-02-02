import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MemberService, Member } from '../../../services/member.service';

@Component({
  selector: 'app-member-list',
  templateUrl: './member-list.component.html',
  styleUrls: ['./member-list.component.css']
})
export class MemberListComponent implements OnInit {
  members: Member[] = [];
  searchName: string = '';

  constructor(
    private memberService: MemberService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadMembers();
  }

  loadMembers(): void {
    this.memberService.getAllMembers().subscribe({
      next: (data) => this.members = data,
      error: (err) => console.error('Error loading members:', err)
    });
  }

  searchMembers(): void {
    if (this.searchName.trim()) {
      this.memberService.searchMembers(this.searchName).subscribe({
        next: (data) => this.members = data,
        error: (err) => console.error('Error searching members:', err)
      });
    } else {
      this.loadMembers();
    }
  }

  deleteMember(id: number): void {
    if (confirm('Are you sure you want to delete this member?')) {
      this.memberService.deleteMember(id).subscribe({
        next: () => this.loadMembers(),
        error: (err) => alert('Error deleting member')
      });
    }
  }

  editMember(id: number): void {
    this.router.navigate(['/members', id, 'edit']);
  }
}
