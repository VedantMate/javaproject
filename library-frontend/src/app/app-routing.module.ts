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
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './services/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'books', component: BookListComponent, canActivate: [AuthGuard] },
  { path: 'books/new', component: BookFormComponent, canActivate: [AuthGuard] },
  { path: 'books/:id/edit', component: BookFormComponent, canActivate: [AuthGuard] },
  { path: 'books/:id/copies', component: BookCopyListComponent, canActivate: [AuthGuard] },
  { path: 'members', component: MemberListComponent, canActivate: [AuthGuard] },
  { path: 'members/new', component: MemberFormComponent, canActivate: [AuthGuard] },
  { path: 'members/:id/edit', component: MemberFormComponent, canActivate: [AuthGuard] },
  { path: 'staff', component: StaffListComponent, canActivate: [AuthGuard] },
  { path: 'staff/new', component: StaffFormComponent, canActivate: [AuthGuard] },
  { path: 'staff/:id/edit', component: StaffFormComponent, canActivate: [AuthGuard] },
  { path: 'issues', component: IssueListComponent, canActivate: [AuthGuard] },
  { path: 'issues/new', component: IssueBookComponent, canActivate: [AuthGuard] },
  { path: 'reports', component: ReportsComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
