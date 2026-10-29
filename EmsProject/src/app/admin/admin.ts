import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UsersService } from '../services/users';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './admin.html',
  styleUrl: './admin.css'
})
export class AdminComponent implements OnInit {

  // ==========================
  // Dashboard Statistics
  // ==========================

  totalEmployees: number = 0;
  workingEmployees: number = 0;
  employeesOnLeave: number = 0;
  leaveRequests: number = 0;
  designationRequests: number = 0;

  // Current year for footer
  currentYear: number = new Date().getFullYear();

  constructor(
    private router: Router,
    private usersService: UsersService,
    private cdr: ChangeDetectorRef
  ) { }

  // ==========================
  // Component Initialization
  // ==========================

  ngOnInit(): void {
    this.loadDashboardStatistics();
  }

  // ==========================
  // Load All Dashboard Counts
  // ==========================

  loadDashboardStatistics(): void {

    this.usersService.getEmployeesCount().subscribe({

      next: (count: number) => {

        this.totalEmployees = Number(count);

        // Force the template to update
        this.cdr.detectChanges();
      },

      error: (error) => {
        console.error('Error fetching employee count:', error);
      }
    });

    // Working Employees
    this.usersService.getCountWorkingEmployee().subscribe({
      next: (count: number) => {
        this.workingEmployees = Number(count);

        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error fetching working employees:', error);
      }
    });


    // Employees On Leave
    this.usersService.getEmployeesOnLeaveCount().subscribe({

  next: (count: number) => {

    this.employeesOnLeave = Number(count);

    // Update the UI immediately
    this.cdr.detectChanges();
  },

  error: (error) => {
    console.error('Error fetching employees on leave:', error);
  }

});


    // Leave Requests
    this.usersService.getCountLeaveRequest().subscribe({
      next: (count: number) => {
        this.leaveRequests = count;

        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error fetching leave requests:', error);
      }
    });


    // Designation Requests
    //   this.usersService.getDesignationRequestsCount().subscribe({
    //     next: (count: number) => {
    //       this.designationRequests = count;
    //     },
    //     error: (error) => {
    //       console.error('Error fetching designation requests:', error);
    //     }
    //   });
  }


  // ==========================
  // Header / Navigation
  // ==========================

  openProfile(): void {
    console.log('Profile clicked');
  }

  addEmployee(): void {
    this.router.navigate(['/AddEmployee']);
  }


  // ==========================
  // Dashboard Cards
  // ==========================

  // viewEmployees(): void {
  //   this.router.navigate(['/ManageEmployee']);
  // }

  viewLeaveRequests(): void {
    this.router.navigate(['/allLeaves'])
  }


  // ==========================
  // Footer
  // ==========================

  goToPrivacy(): void {
    console.log('Privacy Policy clicked');
  }

  goToTerms(): void {
    console.log('Terms clicked');
  }

  goToHelp(): void {
    console.log('Help clicked');
  }
}