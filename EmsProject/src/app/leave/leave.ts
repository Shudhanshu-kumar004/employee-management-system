import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { UsersService } from '../services/users';
import { Leaves } from '../models/leaves';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-leave',
  imports: [CommonModule, DatePipe],
  templateUrl: './leave.html',
  styleUrl: './leave.css',
})
export class LeavesRequestComponent implements OnInit {

  leaves: Leaves[] = [];
  loading = false;

  constructor(
    private usersService: UsersService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadLeaves();
  }

  loadLeaves(): void {

    this.loading = true;

    this.usersService.getAllLeaves()
      .pipe(
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (data: Leaves[]) => {
          this.leaves = data;
          this.loading = false;
        },

        error: (error) => {
          this.leaves = [];
          this.loading = false;
        },

        complete: () => {
        }
      });
  }

  updateLeaveStatus(id: number, status: string): void {

    console.log('Updating leave:', id, status);

    this.usersService.updateLeave(id, status).subscribe({

      next: (response: string) => {

        console.log('Leave updated successfully:', response);

        // Fetch the latest data from the database
        this.loadLeaves();

      },

      error: (error) => {

        console.error('Error updating leave:', error);

      }

    });

  }
}