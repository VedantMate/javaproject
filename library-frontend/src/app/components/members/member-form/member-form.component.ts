import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MemberService, Member } from '../../../services/member.service';

@Component({
  selector: 'app-member-form',
  templateUrl: './member-form.component.html',
  styleUrls: ['./member-form.component.css']
})
export class MemberFormComponent implements OnInit {
  memberForm: FormGroup;
  isEditMode = false;
  memberId?: number;

  constructor(
    private fb: FormBuilder,
    private memberService: MemberService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.memberForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      membershipStatus: ['ACTIVE', Validators.required]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.memberId = +id;
      this.loadMember(this.memberId);
    }
  }

  loadMember(id: number): void {
    this.memberService.getMemberById(id).subscribe({
      next: (member) => this.memberForm.patchValue(member),
      error: (err) => console.error('Error loading member:', err)
    });
  }

  onSubmit(): void {
    if (this.memberForm.valid) {
      const member: Member = this.memberForm.value;
      
      if (this.isEditMode && this.memberId) {
        this.memberService.updateMember(this.memberId, member).subscribe({
          next: () => this.router.navigate(['/members']),
          error: (err) => alert('Error updating member')
        });
      } else {
        this.memberService.createMember(member).subscribe({
          next: () => this.router.navigate(['/members']),
          error: (err) => alert('Error creating member: Member with this email already exists')
        });
      }
    }
  }

  cancel(): void {
    this.router.navigate(['/members']);
  }
}
