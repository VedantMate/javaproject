import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BookCopy {
  id?: number;
  bookId: number;
  rackNo: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookCopyService {
  private apiUrl = 'http://localhost:8080/api/admin/books';

  constructor(private http: HttpClient) { }

  getAllCopies(): Observable<BookCopy[]> {
    return this.http.get<BookCopy[]>(`${this.apiUrl}/copies`);
  }

  getCopiesByBookId(bookId: number): Observable<BookCopy[]> {
    return this.http.get<BookCopy[]>(`${this.apiUrl}/${bookId}/copies`);
  }

  getCopyById(id: number): Observable<BookCopy> {
    return this.http.get<BookCopy>(`${this.apiUrl}/copies/${id}`);
  }

  createCopy(bookId: number, copy: BookCopy): Observable<BookCopy> {
    return this.http.post<BookCopy>(`${this.apiUrl}/${bookId}/copies`, copy);
  }

  updateCopy(id: number, copy: BookCopy): Observable<BookCopy> {
    return this.http.put<BookCopy>(`${this.apiUrl}/copies/${id}`, copy);
  }

  deleteCopy(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/copies/${id}`);
  }

  getCopiesByStatus(status: string): Observable<BookCopy[]> {
    return this.http.get<BookCopy[]>(`${this.apiUrl}/copies/status/${status}`);
  }
}
