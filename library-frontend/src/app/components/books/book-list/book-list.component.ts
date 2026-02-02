import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookService, Book } from '../../../services/book.service';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  books: Book[] = [];
  searchTitle: string = '';
  searchAuthor: string = '';

  constructor(
    private bookService: BookService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
    this.bookService.getAllBooks().subscribe({
      next: (data) => this.books = data,
      error: (err) => console.error('Error loading books:', err)
    });
  }

  searchByTitle(): void {
    if (this.searchTitle.trim()) {
      this.bookService.searchByTitle(this.searchTitle).subscribe({
        next: (data) => this.books = data,
        error: (err) => console.error('Error searching books:', err)
      });
    } else {
      this.loadBooks();
    }
  }

  searchByAuthor(): void {
    if (this.searchAuthor.trim()) {
      this.bookService.searchByAuthor(this.searchAuthor).subscribe({
        next: (data) => this.books = data,
        error: (err) => console.error('Error searching books:', err)
      });
    } else {
      this.loadBooks();
    }
  }

  deleteBook(id: number): void {
    if (confirm('Are you sure you want to delete this book?')) {
      this.bookService.deleteBook(id).subscribe({
        next: () => this.loadBooks(),
        error: (err) => alert('Error deleting book: ' + err.error?.message || 'Cannot delete book with issued copies')
      });
    }
  }

  viewCopies(bookId: number): void {
    this.router.navigate(['/books', bookId, 'copies']);
  }

  editBook(id: number): void {
    this.router.navigate(['/books', id, 'edit']);
  }
}
