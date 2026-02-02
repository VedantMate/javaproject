import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BookListComponent } from './components/books/book-list/book-list.component';
import { BookFormComponent } from './components/books/book-form/book-form.component';
import { BookCopyListComponent } from './components/book-copies/book-copy-list/book-copy-list.component';
import { MemberListComponent } from './components/members/member-list/member-list.component';
import { MemberFormComponent } from './components/members/member-form/member-form.component';
import { StaffListComponent } from './components/staff/staff-list/staff-list.component';
import { StaffFormComponent } from './components/staff/staff-form/staff-form.component';
import { IssueBookComponent } from './components/issues/issue-book/issue-book.component';
import { IssueListComponent } from './components/issues/issue-list/issue-list.component';
import { ReportsComponent } from './components/reports/reports.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'books', component: BookListComponent },
  { path: 'books/new', component: BookFormComponent },
  { path: 'books/:id/edit', component: BookFormComponent },
  { path: 'books/:id/copies', component: BookCopyListComponent },
  { path: 'members', component: MemberListComponent },
  { path: 'members/new', component: MemberFormComponent },
  { path: 'members/:id/edit', component: MemberFormComponent },
  { path: 'staff', component: StaffListComponent },
  { path: 'staff/new', component: StaffFormComponent },
  { path: 'staff/:id/edit', component: StaffFormComponent },
  { path: 'issues', component: IssueListComponent },
  { path: 'issues/new', component: IssueBookComponent },
  { path: 'reports', component: ReportsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
