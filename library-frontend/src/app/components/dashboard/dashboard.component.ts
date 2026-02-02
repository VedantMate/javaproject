import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../services/report.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  dashboardData: any = {};

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.reportService.getDashboardData().subscribe({
      next: (data) => this.dashboardData = data,
      error: (err) => console.error('Error loading dashboard:', err)
    });
  }
}
