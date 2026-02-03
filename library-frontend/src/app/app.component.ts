import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <nav>
      <div class="nav-container">
        <div class="nav-brand">
          <h2>Library Management System</h2>
        </div>
        <ul class="nav-links">
          <li><a routerLink="/dashboard" routerLinkActive="active">Dashboard</a></li>
          <li><a routerLink="/books" routerLinkActive="active">Books</a></li>
          <li><a routerLink="/members" routerLinkActive="active">Members</a></li>
          <li><a routerLink="/staff" routerLinkActive="active">Staff</a></li>
          <li><a routerLink="/issues" routerLinkActive="active">Issues</a></li>
          <li><a routerLink="/reports" routerLinkActive="active">Reports</a></li>
        </ul>
      </div>
    </nav>
    <div class="container">
      <router-outlet></router-outlet>
    </div>
  `,
  styles: [`
    nav {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      padding: 0;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .nav-container {
      display: flex;
      align-items: center;
      justify-content: space-between;
      max-width: 1400px;
      margin: 0 auto;
      padding: 0 20px;
    }

    .nav-brand h2 {
      color: white;
      margin: 0;
      font-size: 20px;
    }

    .nav-links {
      list-style: none;
      display: flex;
      gap: 10px;
      margin: 0;
      padding: 0;
      flex: 1;
      justify-content: center;
    }

    .nav-links li a {
      color: white;
      text-decoration: none;
      padding: 15px 20px;
      display: block;
      transition: background-color 0.3s;
    }

    .nav-links li a:hover,
    .nav-links li a.active {
      background-color: rgba(255, 255, 255, 0.2);
    }

    .nav-user {
      display: flex;
      align-items: center;
      gap: 15px;
    }

    .container {
      padding: 20px;
      max-width: 1400px;
      margin: 0 auto;
    }
  `]
})
export class AppComponent {
  title = 'Library Management System';
}
