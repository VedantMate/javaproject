import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../services/report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  inventoryReport: any = {};
  issueReport: any = {};
  fineReport: any = {};
  
  startDate: string = '';
  endDate: string = '';
  activeTab: string = 'inventory';

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.loadInventoryReport();
    this.setDefaultDates();
  }

  setDefaultDates(): void {
    const today = new Date();
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
    
    this.endDate = today.toISOString().split('T')[0];
    this.startDate = firstDay.toISOString().split('T')[0];
  }

  loadInventoryReport(): void {
    this.reportService.getInventoryReport().subscribe({
      next: (data) => this.inventoryReport = data,
      error: (err) => console.error('Error loading inventory report:', err)
    });
  }

  loadIssueReport(): void {
    if (this.startDate && this.endDate) {
      this.reportService.getIssueReport(this.startDate, this.endDate).subscribe({
        next: (data) => this.issueReport = data,
        error: (err) => console.error('Error loading issue report:', err)
      });
    }
  }

  loadFineReport(): void {
    if (this.startDate && this.endDate) {
      this.reportService.getFineReport(this.startDate, this.endDate).subscribe({
        next: (data) => this.fineReport = data,
        error: (err) => console.error('Error loading fine report:', err)
      });
    }
  }

  switchTab(tab: string): void {
    this.activeTab = tab;
    
    if (tab === 'inventory') {
      this.loadInventoryReport();
    } else if (tab === 'issues') {
      this.loadIssueReport();
    } else if (tab === 'fines') {
      this.loadFineReport();
    }
  }

  getDaysOverdue(dueDate: string): number {
    const due = new Date(dueDate);
    const today = new Date();
    const diffTime = Math.abs(today.getTime() - due.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
  }
}
