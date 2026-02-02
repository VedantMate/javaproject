import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <nav>
      <ul>
        <li><a routerLink="/dashboard" routerLinkActive="active">Dashboard</a></li>
        <li><a routerLink="/books" routerLinkActive="active">Books</a></li>
        <li><a routerLink="/members" routerLinkActive="active">Members</a></li>
        <li><a routerLink="/staff" routerLinkActive="active">Staff</a></li>
        <li><a routerLink="/issues" routerLinkActive="active">Issues</a></li>
        <li><a routerLink="/reports" routerLinkActive="active">Reports</a></li>
      </ul>
    </nav>
    <div class="container">
      <router-outlet></router-outlet>
    </div>
  `,
  styles: []
})
export class AppComponent {
  title = 'Library Management System';
}
