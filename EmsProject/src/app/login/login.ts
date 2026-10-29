
import { CommonModule } from '@angular/common';
import { Component, ChangeDetectorRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import {
  ApiResponse,
  LoginResponseDto
} from '../models/login-response-dto';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  loginData = {
    emailId: '',
    password: '',
    rememberMe: false
  };

  showPassword = false;
  isSubmitting = false;
  loginError = '';

  private readonly apiUrl =
    'http://localhost:8080/api/v1/ems/login';

  constructor(
    private http: HttpClient,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  onSubmit(): void {

    // Prevent multiple requests
    if (this.isSubmitting) {
      return;
    }

    // Clear previous error
    this.loginError = '';

    // Validate input
    if (
      !this.loginData.emailId.trim() ||
      !this.loginData.password
    ) {
      this.loginError =
        'Please enter your Email Id and password.';
      return;
    }

    this.isSubmitting = true;

    const loginPayload = {
      emailId: this.loginData.emailId.trim(),
      password: this.loginData.password
    };

    this.http.post<ApiResponse<LoginResponseDto>>(
      this.apiUrl,
      loginPayload
    )
    .pipe(
      finalize(() => {
        this.isSubmitting = false;
      })
    )
    .subscribe({

      next: (response) => {

        // console.log('Backend response:', response);

        const loginData = response.data;

        if (
          response.status === 200 &&
          loginData?.token &&
          (
            loginData.role === 'ADMIN' ||
            loginData.role === 'EMPLOYEE'
          )
        ) {

          // Save authentication details
          localStorage.setItem(
            'token',
            loginData.token
          );

          localStorage.setItem(
            'role',
            loginData.role
          );

          localStorage.setItem(
            'emailId',
            loginData.emailId
          );

          // Save the complete login response data
          localStorage.setItem(
            'employee',
            JSON.stringify(loginData)
          );

          // console.log('JWT saved successfully.');
          // console.log('Logged-in role:', loginData.role);
          // console.log(
          //   'Employee data saved:',
          //   localStorage.getItem('employee')
          // );

          // Redirect according to role
          if (loginData.role === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/EmployeePageComponent']);
          }

        } else {
          this.loginError =
            'Login failed. Invalid response from server.';
        }
      },

      error: (error) => {

        console.error('Login failed:', error);

        if (error.status === 401 || error.status === 403) {
          this.loginError = 'Invalid Email Id or password.';
        } else if (error.error?.message) {
          this.loginError = error.error.message;
        } else if (typeof error.error === 'string') {
          this.loginError = error.error;
        } else if (error.status >= 500) {
          this.loginError =
            'Server error. Please try again later.';
        } else {
          this.loginError =
            'Login failed. Please try again.';
        }

        // Clear login fields
        this.loginData.emailId = '';
        this.loginData.password = '';
        this.loginData.rememberMe = false;

        this.cdr.detectChanges();
      }
    });
  }
}
