import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Employee } from '../models/employee';
import { UsersService } from '../services/users';
import { Subject } from 'rxjs';
import Swal from 'sweetalert2';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';


@Component({
  selector: 'app-manage-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-employee.html',
  styleUrl: './manage-employee.css'
})


export class ManageEmployeeComponent implements OnInit {

  searchText = '';
  selectedStatus = 'ALL';
  employees: Employee[] = [];

  // ---------- Pagination state ----------
  currentPage = 0;       // 0-based, matches backend pageNo
  pageSize = 10;
  sortBy = 'name';
  totalPages = 0;
  totalElements = 0;
  // ---------------------------------------

  private searchTimeout: any;
  private searchSubject = new Subject<string>();

  constructor(
    private router: Router,
    private usersService: UsersService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadEmployees();

    this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(keyword => {

        if (keyword.trim() === '' && this.selectedStatus === 'ALL') {
          this.currentPage = 0;
          return this.usersService.getEmployeesPagination(
            this.currentPage,
            this.pageSize,
            this.sortBy
          );
        }

        return this.usersService.filterSearchUsers(
          keyword,
          this.selectedStatus
        );
      })

    ).subscribe({
  next: (response: any) => {
    if (response && response.data && response.data.content) {
      this.employees = response.data.content;
      this.totalPages = response.data.totalPages;
      this.totalElements = response.data.totalElements;
    } else {
      this.employees = response;
    }
    this.cdr.detectChanges();
  },
  error: err => {
    console.error(err);
    this.employees = [];
    this.cdr.detectChanges();
  }
});
  }

  loadEmployees(): void {

    if (this.searchText.trim() === '' && this.selectedStatus === 'ALL') {

      this.usersService.getEmployeesPagination(
  this.currentPage,
  this.pageSize,
  this.sortBy
).subscribe({
  next: (response: any) => {
    console.log('Pagination API Response:', response);
    this.employees = response.data.content;
    this.totalPages = response.data.totalPages;
    this.totalElements = response.data.totalElements;
    this.cdr.detectChanges();
  },
  error: err => {
    console.error(err);
    this.employees = [];
    this.cdr.detectChanges();
  }
});

    } else {

      this.usersService.filterSearchUsers(
        this.searchText,
        this.selectedStatus
      ).subscribe({
        next: data => {
          console.log('Filter API Response:', data);
          this.employees = data;
          this.cdr.detectChanges();
        },
        error: err => {
          console.error(err);
          this.employees = [];
        }
      });

    }
  }

  // ---------- Pagination controls ----------
  goToPreviousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadEmployees();
    }
  }

  goToNextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadEmployees();
    }
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadEmployees();
    }
  }
  // ------------------------------------------

  onStatusChange(): void {
    this.currentPage = 0;
    this.loadEmployees();
  }

  onSearchInput(): void {
    this.searchSubject.next(this.searchText);
  }

  goBack(): void {
    this.router.navigate(['/admin']);
  }

  addEmployee(): void {
    this.router.navigate(['/AddEmployee']);
  }

  viewEmployee(id: number, index: number) {
    this.router.navigate(
      ['/viewEmployee', id],
      {
        state: {
          slNo: index + 1
        }
      }
    );
  }

  updateEmployee(id: number): void {
    this.router.navigate(['/update-employee', id]);
  }


  deleteEmployee(id: number): void {

    Swal.fire({
      title: 'Delete Employee?',
      text: "This action can't be undone.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc2626',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Yes, delete it',
      cancelButtonText: 'Cancel'
    }).then((result) => {

      if (!result.isConfirmed) return;

      this.usersService.deleteEmployee(id).subscribe({

        next: (message: string) => {

          if (message === "User deleted!") {

            Swal.fire({
              title: 'Employee Deleted',
              text: 'The employee has been removed successfully.',
              icon: 'success',
              timer: 1500,
              showConfirmButton: false
            }).then(() => {
              window.location.reload();
            });

          } else {

            Swal.fire({
              title: 'Action Not Allowed',
              text: 'This account is an Admin.',
              icon: 'info',
              confirmButtonText: 'OK'
            });

          }

        },

        error: (error) => {

          console.error('Delete failed', error);

          Swal.fire({
            title: 'Delete Failed',
            text: 'Something went wrong while deleting the employee.',
            icon: 'error'
          });

        }

      });

    });


  }
}