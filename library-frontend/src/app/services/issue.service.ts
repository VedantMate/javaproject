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
  private apiUrl = 'http://localhost:8080/api/staff/issues';

  constructor(private http: HttpClient) { }

  getAllIssues(): Observable<BookIssue[]> {
    return this.http.get<BookIssue[]>(this.apiUrl);
  }

  getIssueById(id: number): Observable<BookIssue> {
    return this.http.get<BookIssue>(`${this.apiUrl}/${id}`);
  }

  getIssuesByMember(memberId: number): Observable<BookIssue[]> {
    return this.http.get<BookIssue[]>(`${this.apiUrl}/member/${memberId}`);
  }

  getActiveIssues(): Observable<BookIssue[]> {
    return this.http.get<BookIssue[]>(`${this.apiUrl}/active`);
  }

  issueBook(memberId: number, bookCopyId: number): Observable<BookIssue> {
    return this.http.post<BookIssue>(this.apiUrl, { memberId, bookCopyId });
  }

  returnBook(id: number): Observable<BookIssue> {
    return this.http.put<BookIssue>(`${this.apiUrl}/${id}/return`, {});
  }

  getOverdueBooks(): Observable<BookIssue[]> {
    return this.http.get<BookIssue[]>(`${this.apiUrl}/overdue`);
  }

  calculateFine(id: number): Observable<{ fine: number }> {
    return this.http.get<{ fine: number }>(`${this.apiUrl}/${id}/fine`);
  }
}
