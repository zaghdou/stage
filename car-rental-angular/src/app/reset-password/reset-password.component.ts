// reset-password.component.ts

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AuthService } from '../auth/components/services/auth/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm!: FormGroup;
  token: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private message: NzMessageService
  ) {}

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParams['token'];
    
    this.resetPasswordForm = this.fb.group({
      newPassword: [null, [Validators.required, Validators.minLength(6)]]
    });
  }

  submit(): void {
    if (this.resetPasswordForm.invalid) {
      this.message.error('Please provide a new password');
      return;
    }

    this.authService.resetPassword(this.token, this.resetPasswordForm.value.newPassword).subscribe(
      () => {
        this.message.success('Password reset successfully');
        this.router.navigateByUrl('/login');
      },
      err => {
        this.message.error('An error occurred while resetting password');
      }
    );
  }
}
