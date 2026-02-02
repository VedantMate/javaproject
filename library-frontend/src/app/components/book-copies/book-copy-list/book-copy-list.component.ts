import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookCopyService, BookCopy } from '../../../services/book-copy.service';
import { BookService, Book } from '../../../services/book.service';

@Component({
  selector: 'app-book-copy-list',
  templateUrl: './book-copy-list.component.html',
  styleUrls: ['./book-copy-list.component.css']
})
export class BookCopyListComponent implements OnInit {
  copies: BookCopy[] = [];
  book?: Book;
  bookId!: number;
  newCopy: BookCopy = { bookId: 0, rackNo: '', status: 'AVAILABLE' };
  showForm = false;

  constructor(
    private bookCopyService: BookCopyService,
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.bookId = +id;
      this.newCopy.bookId = this.bookId;
      this.loadBook();
      this.loadCopies();
    }
  }

  loadBook(): void {
    this.bookService.getBookById(this.bookId).subscribe({
      next: (data) => this.book = data,
      error: (err) => console.error('Error loading book:', err)
    });
  }

  loadCopies(): void {
    this.bookCopyService.getCopiesByBookId(this.bookId).subscribe({
      next: (data) => this.copies = data,
      error: (err) => console.error('Error loading copies:', err)
    });
  }

  addCopy(): void {
    this.bookCopyService.createCopy(this.bookId, this.newCopy).subscribe({
      next: () => {
        this.loadCopies();
        this.newCopy = { bookId: this.bookId, rackNo: '', status: 'AVAILABLE' };
        this.showForm = false;
      },
      error: (err) => alert('Error creating copy: ' + (err.error?.message || err.message))
    });
  }

  updateCopyStatus(copy: BookCopy, newStatus: string): void {
    const updated = { ...copy, status: newStatus };
    this.bookCopyService.updateCopy(copy.id!, updated).subscribe({
      next: () => this.loadCopies(),
      error: (err) => alert('Error updating copy: ' + (err.error?.message || err.message))
    });
  }

  deleteCopy(id: number): void {
    if (confirm('Are you sure you want to delete this copy?')) {
      this.bookCopyService.deleteCopy(id).subscribe({
        next: () => this.loadCopies(),
        error: (err) => alert('Error deleting copy: ' + (err.error?.message || err.message))
      });
    }
  }

  goBack(): void {
    this.router.navigate(['/books']);
  }
}
