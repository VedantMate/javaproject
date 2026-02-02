import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BookListComponent } from './components/books/book-list/book-list.component';
import { BookFormComponent } from './components/books/book-form/book-form.component';
import { BookCopyListComponent } from './components/book-copies/book-copy-list/book-copy-list.component';
import { BookCopyFormComponent } from './components/book-copies/book-copy-form/book-copy-form.component';
import { MemberListComponent } from './components/members/member-list/member-list.component';
import { MemberFormComponent } from './components/members/member-form/member-form.component';
import { StaffListComponent } from './components/staff/staff-list/staff-list.component';
import { StaffFormComponent } from './components/staff/staff-form/staff-form.component';
import { IssueBookComponent } from './components/issues/issue-book/issue-book.component';
import { IssueListComponent } from './components/issues/issue-list/issue-list.component';
import { ReportsComponent } from './components/reports/reports.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    BookListComponent,
    BookFormComponent,
    BookCopyListComponent,
    BookCopyFormComponent,
    MemberListComponent,
    MemberFormComponent,
    StaffListComponent,
    StaffFormComponent,
    IssueBookComponent,
    IssueListComponent,
    ReportsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
