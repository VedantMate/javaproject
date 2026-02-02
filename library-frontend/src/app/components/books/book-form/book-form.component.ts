import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookService, Book } from '../../../services/book.service';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.css']
})
export class BookFormComponent implements OnInit {
  bookForm: FormGroup;
  isEditMode = false;
  bookId?: number;

  constructor(
    private fb: FormBuilder,
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.bookForm = this.fb.group({
      title: ['', Validators.required],
      author: ['', Validators.required],
      isbn: ['', Validators.required],
      category: ['', Validators.required],
      status: ['ACTIVE', Validators.required]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.bookId = +id;
      this.loadBook(this.bookId);
    }
  }

  loadBook(id: number): void {
    this.bookService.getBookById(id).subscribe({
      next: (book) => this.bookForm.patchValue(book),
      error: (err) => console.error('Error loading book:', err)
    });
  }

  onSubmit(): void {
    if (this.bookForm.valid) {
      const book: Book = this.bookForm.value;
      
      if (this.isEditMode && this.bookId) {
        this.bookService.updateBook(this.bookId, book).subscribe({
          next: () => this.router.navigate(['/books']),
          error: (err) => alert('Error updating book: ' + (err.error?.message || err.message))
        });
      } else {
        this.bookService.createBook(book).subscribe({
          next: () => this.router.navigate(['/books']),
          error: (err) => alert('Error creating book: ' + (err.error?.message || err.message))
        });
      }
    }
  }

  cancel(): void {
    this.router.navigate(['/books']);
  }
}
