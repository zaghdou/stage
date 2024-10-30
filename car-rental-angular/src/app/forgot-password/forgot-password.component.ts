// forgot-password.component.ts

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';
import { AuthService } from '../auth/components/services/auth/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
  forgotPasswordForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.forgotPasswordForm = this.fb.group({
      email: [null, [Validators.email, Validators.required]]
    });
  }

  submit(): void {
    if (this.forgotPasswordForm.invalid) {
      this.message.error('Please provide a valid email address');
      return;
    }

    this.authService.forgotPassword(this.forgotPasswordForm.value.email).subscribe(
      () => {
        this.message.success('If an account with that email exists, a password reset link will be sent.');
        this.router.navigateByUrl('/login');
      },
      err => {
        this.message.error('An error occurred while requesting password reset');
      }
    );
  }
}
