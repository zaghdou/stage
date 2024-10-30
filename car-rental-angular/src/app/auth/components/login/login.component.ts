import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth/auth.service';
import { StorageService } from '../services/storage/storage.service';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  isSpinning: boolean = false;
  loginForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private message: NzMessageService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required]]
    });
  }

  login(): void {
    if (this.loginForm.invalid) {
      this.message.error('Please fill in all required fields');
      return;
    }

    this.isSpinning = true;
    this.authService.login(this.loginForm.value).subscribe(
      res => {
        this.isSpinning = false;

        if (res && res.userId && res.userRole && res.jwt) {
          // Save user data and token
          StorageService.saveUser({ id: res.userId, userRole: res.userRole });
          StorageService.saveToken(res.jwt);

          // Redirect based on user role
          switch (res.userRole) {
            case 'ADMIN':
              this.router.navigateByUrl('/admin/dashboard');
              break;
            case 'CUSTOMER':
              this.router.navigateByUrl('/customer/dashboard');
              break;
            case 'AGENT_IMMOBILIER':
              this.router.navigateByUrl('/agent/dashboard');
              break;
            default:
              this.message.error('Unknown user role', { nzDuration: 3000 });
              break;
          }
        } else {
          this.message.error('Login failed, please check your credentials', { nzDuration: 3000 });
        }
      },
      err => {
        this.isSpinning = false;
        if (err.error && err.error.message) {
          this.message.error(err.error.message, { nzDuration: 3000 });
        } else {
          this.message.error('An error occurred during login', { nzDuration: 3000 });
        }
      }
    );
  }

  // Method to navigate to forgot password page
  navigateToForgotPassword(): void {
    this.router.navigateByUrl('/forgot-password');
  }
}
