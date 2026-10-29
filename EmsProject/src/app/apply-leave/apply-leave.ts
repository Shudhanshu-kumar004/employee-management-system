import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsersService } from '../services/users';

@Component({
  selector: 'app-apply-leave',
  standalone: true,
  imports: [FormsModule],
  styleUrl: './apply-leave.css',
  templateUrl: './apply-leave.html',
})
export class ApplyLeaveComponent {

  private userService = inject(UsersService);

  leaveData = {
    fromDate: '',
    toDate: '',
    reason: ''
  };

  submitLeave() {
  const employeeData = localStorage.getItem('employee');

  if (!employeeData) {
    alert('Employee details not found. Please log in again.');
    return;
  }

  const employee = JSON.parse(employeeData);

  const requestData = {
    ...this.leaveData,
    userId: employee.id
  };

  this.userService.leaveRequest(requestData).subscribe({
    next: (response) => {
      console.log('Leave request submitted:', response);
      alert('Leave request submitted successfully!');

      this.leaveData = {
        fromDate: '',
        toDate: '',
        reason: ''
      };
    },
    error: (error) => {
      console.error('Error submitting leave:', error);
      alert(error.error?.message || 'Failed to submit leave request.');
    }
  });
}
}