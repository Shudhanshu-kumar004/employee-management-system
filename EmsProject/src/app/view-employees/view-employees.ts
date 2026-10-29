import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UsersService } from '../services/users';
import { ViewEmployee } from '../models/view-employee';

@Component({
  standalone: true,
  selector: 'app-view-employees',
  imports: [CommonModule],
  templateUrl: './view-employees.html',
  styleUrl: './view-employees.css'
})
export class ViewEmployeesComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private usersService = inject(UsersService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  employee: ViewEmployee | null = null;
  slNo = history.state?.slNo ?? 1;

  ngOnInit(): void {

    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Route ID:', id);

    this.usersService.getEmployeeDetails(id).subscribe({
      next: (data) => {
        console.log('Employee Data:', data);

        this.employee = data;
        this.cdr.detectChanges();   // ← Important
      },
      error: (err) => {
        console.error('API Error:', err);
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/ManageEmployee']);
  }

  getInitial(name?: string): string {
    return name ? name.charAt(0).toUpperCase() : 'E';
  }
}