// auth.service.ts

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

const BASE_URL = 'http://localhost:8081/api/auth'; // Assurez-vous que l'URL est correcte

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  register(signupRequest: any): Observable<any> {
    return this.http.post(`${BASE_URL}/signup/customer`, signupRequest).pipe(
      catchError(error => {
        console.error('Registration error:', error);
        return throwError(() => new Error('Registration failed'));
      })
    );
  }

  login(loginRequest: any): Observable<any> {
    return this.http.post(`${BASE_URL}/login`, loginRequest).pipe(
      catchError(error => {
        console.error('Login error:', error);
        return throwError(() => new Error('Login failed'));
      })
    );
  }

  registeragent(signupRequest: any): Observable<any> {
    return this.http.post(`${BASE_URL}/signup/agentimmob`, signupRequest).pipe(
      catchError(error => {
        console.error('Agent registration error:', error);
        return throwError(() => new Error('Agent registration failed'));
      })
    );
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${BASE_URL}/forgot-password`, null, { params: { email } }).pipe(
      catchError(error => {
        console.error('Forgot password error:', error);
        return throwError(() => new Error('Forgot password failed'));
      })
    );
  }

  resetPassword(token: string, newPassword: string): Observable<any> {
    return this.http.post(`${BASE_URL}/reset-password`, null, { params: { token, newPassword } }).pipe(
      catchError(error => {
        console.error('Reset password error:', error);
        return throwError(() => new Error('Reset password failed'));
      })
    );
  }
}
