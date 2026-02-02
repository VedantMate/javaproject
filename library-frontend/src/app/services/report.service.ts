import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private apiUrl = 'http://localhost:8080/api/manager/reports';

  constructor(private http: HttpClient) { }

  getInventoryReport(): Observable<any> {
    return this.http.get(`${this.apiUrl}/inventory`);
  }

  getIssueReport(startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/issues?startDate=${startDate}&endDate=${endDate}`);
  }

  getFineReport(startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/fines?startDate=${startDate}&endDate=${endDate}`);
  }

  getDashboardData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/dashboard`);
  }
}
