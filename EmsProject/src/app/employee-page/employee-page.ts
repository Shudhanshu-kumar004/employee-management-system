
import { Component, OnInit } from '@angular/core';
import {
  CommonModule,
  CurrencyPipe,
  DatePipe,
  TitleCasePipe
} from '@angular/common';
import { Router, RouterLink } from '@angular/router';

type EmployeeRole = 'ADMIN' | 'EMPLOYEE';
type EmployeeStatus = 'ACTIVE' | 'INACTIVE' | 'ON_LEAVE';

interface Employee {
  id: number;
  name: string;
  emailId: string;
  phoneNumber: string;
  address: string;
  department: string;
  designation: string;
  role: EmployeeRole;
  salary: number;
  status: EmployeeStatus;
  joiningDate: string;
  createdAt: string;
  updatedAt: string;
}

@Component({
  selector: 'app-employee-page',
  standalone: true,
  imports: [
    CommonModule,
    CurrencyPipe,
    DatePipe,
    TitleCasePipe,
    RouterLink
  ],
  templateUrl: './employee-page.html',
  styleUrl: './employee-page.css'
})
export class EmployeePageComponent implements OnInit {

    constructor(private router: Router) {}

  employee: Employee | null = null;

  loading = true;
  errorMessage = '';

  ngOnInit(): void {
    try {
      const employeeData = localStorage.getItem('employee');

      if (!employeeData) {
        this.errorMessage =
          'Employee details not found. Please log in again.';
        return;
      }

      this.employee = JSON.parse(employeeData) as Employee;

    } catch (error) {
      console.error('Failed to load employee details:', error);
      this.errorMessage = 'Unable to load employee profile.';
    } finally {
      this.loading = false;
    }
  }

  getInitials(name: string): string {
    if (!name?.trim()) {
      return '?';
    }

    const parts = name.trim().split(/\s+/);

    return (
      parts.length > 1
        ? parts[0][0] + parts[parts.length - 1][0]
        : parts[0].slice(0, 2)
    ).toUpperCase();
  }

  getStatusClass(status: EmployeeStatus): string {
    switch (status) {
      case 'ACTIVE':
        return 'status-active';
      case 'ON_LEAVE':
        return 'status-leave';
      default:
        return 'status-inactive';
    }
  }

  logout(): void {
    console.log("Logout button called!");
    

  localStorage.removeItem('token');
  localStorage.removeItem('role');
  localStorage.removeItem('emailId');
  localStorage.removeItem('employee');

  this.router.navigate(['/login']);
}


}
