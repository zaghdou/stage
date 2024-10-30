import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  isSpinning: boolean = false;
  signupForm!: FormGroup;
  UserRole: string[] = ['AGENT_IMMOBILIER', 'CUSTOMER'];
  isAgentRegistration: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.isAgentRegistration = params['userRole'] === 'agent';
    });

    this.signupForm = this.fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidator]],
      userRole: [null, [Validators.required]] // Assurez-vous que le nom est correct ici
    });
  }

  confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } else if (control.value !== this.signupForm.controls['password'].value) {
      return { confirm: true, error: true };
    }
    return {};
  }

  register(): void {
    const formValue = this.signupForm.value;
    const endpoint = formValue.userRole === 'AGENT_IMMOBILIER'
      ? 'registeragent'
      : 'register';
  
    console.log('Sending data:', formValue);
    
    this.authService[endpoint](formValue).subscribe(
      res => {
        console.log('Response:', res);
        if (res.id !== null) {
          this.message.success('User registered successfully', {
            nzDuration: 5000
          });
          this.router.navigateByUrl('/login');
        } else {
          this.message.error('User registration failed', {
            nzDuration: 5000
          });
        }
      },
      err => {
        console.log('Error:', err);
      }
    );
  }
  
}
