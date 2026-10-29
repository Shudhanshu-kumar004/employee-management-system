import { Component, OnInit, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule
} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { UsersService } from '../services/users';

@Component({
  selector: 'app-edit-profile',
  standalone: true,
  imports: [ReactiveFormsModule],
  styleUrl: './edit-profile.css',
  templateUrl: './edit-profile.html',
})
export class EditProfileComponent implements OnInit {

  private usersService = inject(UsersService);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  profileForm!: FormGroup;
  isSubmitting = false;

  employeeId!: number;

  ngOnInit(): void {

    this.employeeId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.profileForm = this.fb.group({
      name: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.pattern(/^[a-zA-Z ]+$/)
        ]
      ],

      emailId: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],

      phoneNumber: [
        '',
        [
          Validators.required,
          Validators.pattern(/^[6-9]\d{9}$/)
        ]
      ],

      address: [
        '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(250)
        ]
      ]
    });

    // TODO: Load the existing employee profile from your backend.
  }

  updateProfile(): void {

    if (this.profileForm.invalid || this.isSubmitting) {
      this.profileForm.markAllAsTouched();
      return;
    }

    if (!Number.isInteger(this.employeeId) || this.employeeId <= 0) {
      console.error('Invalid employee ID:', this.employeeId);
      alert('Invalid employee ID.');
      return;
    }

    const profileData = this.profileForm.getRawValue();

    this.isSubmitting = true;

    this.usersService.updateProfile(this.employeeId, profileData)
      .subscribe({
        next: (response: any) => {
          console.log('Profile updated successfully:', response);
          alert('Profile updated successfully!');

          this.isSubmitting = false;
          this.goBack();
        },

        error: (error: HttpErrorResponse) => {
          console.error('Update failed:', error);
          console.error('Status:', error.status);
          console.error('Backend error:', error.error);

          alert(error.error?.message || 'Failed to update profile.');
          this.isSubmitting = false;
        }
      });
  }

  goBack(): void {
    this.router.navigate(['/EmployeePageComponent']);
  }
}