
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-add-employee',
  styleUrl: './add-employee.css',
  templateUrl: './add-employee.html',
})
export class AddEmployeeComponent {

  private router = inject(Router);

  private fb = inject(FormBuilder);
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/ems/save';

  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  employeeForm = this.fb.group({
    name: ['', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50),
      Validators.pattern(/^[a-zA-Z ]+$/)
    ]],

    emailId: ['', [
      Validators.required,
      Validators.email,
      Validators.maxLength(255)
    ]],

    phoneNumber: ['', [
      Validators.required,
      Validators.pattern(/^[6-9]\d{9}$/)
    ]],

    department: ['', [
      Validators.required,
      Validators.maxLength(255)
    ]],

    designation: ['', [
      Validators.required,
      Validators.maxLength(255)
    ]],

    joiningDate: ['', [
      Validators.required
    ]],

    salary: [null as number | null, [
      Validators.required,
      Validators.min(0.01)
    ]],

    role: ['', [
      Validators.required
    ]],

    status: ['', [
      Validators.required
    ]],

    password: ['', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(72)
    ]],

    address: ['', [
      Validators.required,
      Validators.maxLength(255)
    ]]
  });

  isInvalid(fieldName: string): boolean {
    const field = this.employeeForm.get(fieldName);

    return !!field &&
      field.invalid &&
      (field.dirty || field.touched);
  }

  goBack(): void{
    this.router.navigate(['/admin']);
  }

  onSubmit(): void {

    console.log("submit button called");
    this.successMessage = '';
    this.errorMessage = '';

    if (this.employeeForm.invalid) {
      this.employeeForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;

    const employeeData = this.employeeForm.getRawValue();

    this.http.post(this.apiUrl, employeeData).subscribe({
      next: () => {
        this.successMessage = 'Employee added successfully!';
        this.employeeForm.reset();
        this.isSubmitting = false;
      },

      error: (error) => {
        this.errorMessage =
          error.error?.message ||
          'Failed to add employee. Please try again.';

        this.isSubmitting = false;

        console.error('Add employee error:', error);
      }
    });
  }

  resetForm(): void {
    this.employeeForm.reset();
    this.successMessage = '';
    this.errorMessage = '';
  }
}
