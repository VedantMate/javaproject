import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StaffService, Staff } from '../../../services/staff.service';

@Component({
  selector: 'app-staff-list',
  templateUrl: './staff-list.component.html',
  styleUrls: ['./staff-list.component.css']
})
export class StaffListComponent implements OnInit {
  staff: Staff[] = [];

  constructor(
    private staffService: StaffService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadStaff();
  }

  loadStaff(): void {
    this.staffService.getAllStaff().subscribe({
      next: (data) => this.staff = data,
      error: (err) => console.error('Error loading staff:', err)
    });
  }

  deleteStaff(id: number): void {
    if (confirm('Are you sure you want to delete this staff member?')) {
      this.staffService.deleteStaff(id).subscribe({
        next: () => this.loadStaff(),
        error: (err) => alert('Error deleting staff')
      });
    }
  }

  editStaff(id: number): void {
    this.router.navigate(['/staff', id, 'edit']);
  }
}
