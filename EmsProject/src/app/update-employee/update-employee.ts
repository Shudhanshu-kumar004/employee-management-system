import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { UsersService } from '../services/users';
import { Employee } from '../models/employee';
import { ViewEmployee } from '../models/view-employee';
import Swal from 'sweetalert2';
import { Location } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-update-employee',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './update-employee.html',
  styleUrl: './update-employee.css'
})
export class UpdateEmployeeComponent implements OnInit {

  employeeForm!: FormGroup;
  id!: number;
  originalEmployee!: Employee;

  private originalValues: Record<string, any> = {};
  private clearedFields = new Set<string>();

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private userService: UsersService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.loadEmployee();
  }

  createForm() {
    this.employeeForm = this.fb.group({
      name: [''],
      emailId: [''],
      phoneNumber: [''],
      password: [{ value: '', disabled: true }],
      role: [{ value: '', disabled: true }],
      department: [''],
      designation: [''],
      salary: [''],
      status: [{ value: '', disabled: true }],
      joiningDate: [{ value: '', disabled: true }],
      address: ['']
    });
  }

  loadEmployee() {
    this.userService.getUpdateEmployees(this.id).subscribe({
      next: (employee: ViewEmployee) => {
        this.originalEmployee = employee;

        this.employeeForm.patchValue({
          name: employee.name,
          emailId: employee.emailId,
          phoneNumber: employee.phoneNumber,
          role: employee.role,
          department: employee.department,
          designation: employee.designation,
          salary: employee.salary,
          status: employee.status,
          joiningDate: employee.joiningDate,
          address: employee.address
        });

        this.originalValues = this.employeeForm.getRawValue();
        this.clearedFields.clear();
      }
    });
  }

  onFocus(fieldName: string) {
    const control = this.employeeForm.get(fieldName);
    if (!control) return;

    if (!(fieldName in this.originalValues)) {
      this.originalValues[fieldName] = control.value;
    }

    if (
      !this.clearedFields.has(fieldName) &&
      control.value === this.originalValues[fieldName]
    ) {
      control.setValue('');
      this.clearedFields.add(fieldName);
    }
  }

  onBlur(fieldName: string) {
    const control = this.employeeForm.get(fieldName);
    if (!control) return;

    if (control.value === '' || control.value === null) {
      control.setValue(this.originalValues[fieldName]);
    } else {
      this.originalValues[fieldName] = control.value;
    }

    this.clearedFields.delete(fieldName);
  }

  updateEmployee() {

  const updatedEmployee = {
    ...this.originalEmployee,
    ...this.employeeForm.getRawValue()
  };

  this.userService.updateEmployee(this.id, updatedEmployee).subscribe({

    next: () => {

      Swal.fire({
        title: 'Employee Updated!',
        text: 'The employee details have been updated successfully.',
        icon: 'success',
        confirmButtonColor: '#2563eb',
        confirmButtonText: 'Back to List'
      }).then(() => {
        this.location.back(); // Returns to the previous page
      });

    },

    error: () => {

      Swal.fire({
        title: 'Update Failed',
        text: 'Something went wrong while updating the employee.',
        icon: 'error',
        confirmButtonColor: '#dc2626'
      });

    }

  });

}

  goBackToManageEmployee(): void {
    this.router.navigate(['/ManageEmployee']);
  }
}
