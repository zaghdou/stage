import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { StorageService } from './auth/components/services/storage/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'] // Assurez-vous que le fichier CSS est bien nommé
})
export class AppComponent implements OnInit {
  title = 'car-rental-angular';

  isCustomerLoggedIn: boolean = false;
  isAdminLoggedIn: boolean = false;
  isAgentLoggedIn: boolean = false;

  constructor(private router: Router) {}

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateLoginStatus();
      }
    });

    // Initial check when the component is loaded
    this.updateLoginStatus();
  }

  updateLoginStatus() {
    this.isCustomerLoggedIn = StorageService.isCustomerLoggedIn();
    this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
    this.isAgentLoggedIn = StorageService.isAgentLoggedIn();
  }

  logout() {
    StorageService.logout();
    this.router.navigateByUrl('/login');
  }
}
