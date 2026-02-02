import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StaffService, Staff } from '../../../services/staff.service';

@Component({
  selector: 'app-staff-form',
  templateUrl: './staff-form.component.html',
  styleUrls: ['./staff-form.component.css']
})
export class StaffFormComponent implements OnInit {
  staffForm: FormGroup;
  isEditMode = false;
  staffId?: number;

  constructor(
    private fb: FormBuilder,
    private staffService: StaffService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.staffForm = this.fb.group({
      name: ['', Validators.required],
      role: ['LIBRARIAN', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      status: ['ACTIVE', Validators.required]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.staffId = +id;
      this.loadStaff(this.staffId);
    }
  }

  loadStaff(id: number): void {
    this.staffService.getStaffById(id).subscribe({
      next: (staff) => this.staffForm.patchValue(staff),
      error: (err) => console.error('Error loading staff:', err)
    });
  }

  onSubmit(): void {
    if (this.staffForm.valid) {
      const staff: Staff = this.staffForm.value;
      
      if (this.isEditMode && this.staffId) {
        this.staffService.updateStaff(this.staffId, staff).subscribe({
          next: () => this.router.navigate(['/staff']),
          error: (err) => alert('Error updating staff')
        });
      } else {
        this.staffService.createStaff(staff).subscribe({
          next: () => this.router.navigate(['/staff']),
          error: (err) => alert('Error creating staff: Staff with this email already exists')
        });
      }
    }
  }

  cancel(): void {
    this.router.navigate(['/staff']);
  }
}
