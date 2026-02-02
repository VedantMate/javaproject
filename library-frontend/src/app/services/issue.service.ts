import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BookIssue {
  id?: number;
  memberId: number;
  bookCopyId: number;
  bookId: number;
  issueDate: string;
  dueDate: string;
  returnDate?: string;
  fine: number;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class IssueService {
  private apiUrl = 'http://localhost:8080/api/circulation';

  constructor(private http: HttpClient) { }

  getAllIssues(): Observable<BookIssue[]> {
    return this.http.get<BookIssue[]>(`${this.apiUrl}/issues`);
  }

  getIssueById(id: number): Observable<BookIssue> {
    return this.http.get<BookIssue>(`${this.apiUrl}/issues/${id}`);
  }

  getIssuesByMember(memberId: number): Observable<BookIssue[]> {
    return this.http.get<BookIssue[]>(`${this.apiUrl}/issues/member/${memberId}`);
  }

  getActiveIssues(): Observable<BookIssue[]> {
    return this.http.get<BookIssue[]>(`${this.apiUrl}/issues/active`);
  }

  issueBook(memberId: number, bookCopyId: number): Observable<BookIssue> {
    return this.http.post<BookIssue>(`${this.apiUrl}/issue`, { memberId, bookCopyId });
  }

  returnBook(id: number): Observable<BookIssue> {
    return this.http.put<BookIssue>(`${this.apiUrl}/return/${id}`, {});
  }

  getOverdueBooks(): Observable<BookIssue[]> {
    return this.http.get<BookIssue[]>(`${this.apiUrl}/issues/overdue`);
  }

  calculateFine(id: number): Observable<{ fine: number }> {
    return this.http.get<{ fine: number }>(`${this.apiUrl}/fine/${id}`);
  }
}
