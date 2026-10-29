import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Employee } from '../models/employee';
import { ViewEmployee } from '../models/view-employee';
import { Leaves } from '../models/leaves';
import { LoginResponseDto } from '../models/login-response-dto';


export interface EditProfileRequest {
  name: string;
  emailId: string;
  phoneNumber: string;
  address: string;
}

export interface LoginRequest {
  emailId: string;
  password: string;
}

export interface ApiResponse<T> {
  message: string;
  status: number;
  data: T;
}


@Injectable({
  providedIn: 'root'
})


export class UsersService {

  private manageApi = 'http://localhost:8080/api/v1/ems/manage/users';
  private filterSearchApi = 'http://localhost:8080/api/v1/ems/filter/search/users';
  private EmployeeDetails = 'http://localhost:8080/api/v1/ems/view/employee/users';
  private updateEmployeeApi = 'http://localhost:8080/api/v1/ems/update/users';
  private deleteEmployeeApi = 'http://localhost:8080/api/v1/ems/delete/users';
  private pagination = 'http://localhost:8080/api/v1/ems';
  private countEmployee = 'http://localhost:8080/api/v1/ems/count';
  private CountLeaves = 'http://localhost:8080/api/v1/ems/count/leaves';
  private CountWorkingEmployee = 'http://localhost:8080/api/v1/ems/count/working/employee';
  private countLeaveRequest = 'http://localhost:8080/api/v1/ems/count/leave/request';
  private AllLeaves = 'http://localhost:8080/api/v1/ems/leaves';
  private updateLeaves = 'http://localhost:8080/api/v1/ems/update/leaves';

  //For Employee page
  private applyLeave = 'http://localhost:8080/api/v1/ems/save/leaves';
  private editEmployee = 'http://localhost:8080/api/v1/ems/profile';

  private http = inject(HttpClient);

  //Jwt token code
  
private readonly loginUrl = 'http://localhost:8080/api/v1/ems/login';

private readonly tokenKey = 'token';


login(
  credentials: LoginRequest
): Observable<ApiResponse<LoginResponseDto>> {

  return this.http
    .post<ApiResponse<LoginResponseDto>>(
      this.loginUrl,
      credentials
    )
    .pipe(
      tap(response => {

        // DEBUG 1: Check the complete API response
        console.log('Login API response:', response);

        if (response.data) {
          const employee = response.data;

          localStorage.setItem('token', employee.token);
          localStorage.setItem('role', employee.role);
          localStorage.setItem('emailId', employee.emailId);

          // Save all employee details
          localStorage.setItem(
            'employee',
            JSON.stringify(employee)
          );

          // DEBUG 2: Confirm the employee was saved
          console.log(
            'Saved employee:',
            localStorage.getItem('employee')
          );
        } else {
          console.error('Login response contains no employee data.');
        }
      })
    );
}




  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }
  //Till Here

  getEmployees(searchText: string = ''): Observable<Employee[]> {
    const params = new HttpParams().set('search', searchText);
    return this.http.get<Employee[]>(this.manageApi, { params });
  }

  filterSearchUsers(
    keyword: string = '',
    status: string = 'ALL'
  ): Observable<Employee[]> {

    let params = new HttpParams();

    if (keyword.trim()) {
      params = params.set('keyword', keyword);
    }

    if (status !== 'ALL') {
      params = params.set('status', status);
    }

    return this.http.get<Employee[]>(this.filterSearchApi, { params });
  }

  getEmployeeDetails(id: number): Observable<ViewEmployee> {
    return this.http.get<ViewEmployee>(`${this.EmployeeDetails}/${id}`);
  }

  // Get single employee by ID (used in UpdateEmployeeComponent)
  getUpdateEmployees(id: number): Observable<ViewEmployee> {
    return this.http.get<ViewEmployee>(`${this.updateEmployeeApi}/${id}`);
  }

  // Update employee
  updateEmployee(id: number, employee: ViewEmployee): Observable<ViewEmployee> {
    return this.http.patch<ViewEmployee>(`${this.updateEmployeeApi}/${id}`, employee);
  }

  deleteEmployee(id: number): Observable<string> {
    return this.http.delete(`${this.deleteEmployeeApi}/${id}`, {
      responseType: 'text'
    });
  }

  //pagination------------------------

  getEmployeesPagination(
    pageNo: number = 0,
    pageSize: number = 10,
    sortBy: string = 'name'):
    Observable<any> {
    return this.http.get<any>(`${this.pagination}/findall/pagination?pageNo=${pageNo}&pageSize=${pageSize}&sortBy=${sortBy}`
    );
  }
  // ----------------------------------

  //Count-------------------------------
  getEmployeesCount() {
    return this.http.get<number>(this.countEmployee);
  }

  //Leaves Count----------------------
  getEmployeesOnLeaveCount() {
    return this.http.get<number>(this.CountLeaves);
  }

  //Working Employee------------------
  getCountWorkingEmployee() {
    return this.http.get<number>(this.CountWorkingEmployee);
  }

  //countLeaveRequest-------------------
  getCountLeaveRequest() {
    return this.http.get<number>(this.countLeaveRequest);
  }

  //Get All Leaves
  getAllLeaves() {
    return this.http.get<Leaves[]>(this.AllLeaves);
  }

  //update leaves
  updateLeave(id: number, status: string): Observable<string> {
    return this.http.put(
      `${this.updateLeaves}/${id}`,
      { status },
      { responseType: 'text' }
    );
  }

  //Leave request
  leaveRequest(leaveData: any) {
  return this.http.post(
    this.applyLeave,
    leaveData,
    { responseType: 'text' }
  );
}


//save Employee
saveEmployee(employeeData: any) {
  return this.http.post(
    'http://localhost:8080/api/v1/ems/save',
    employeeData
  );
}

getProfile(employeeId: number): Observable<EditProfileRequest> {
  return this.http.get<EditProfileRequest>(
    `${this.editEmployee}/${employeeId}`
  );
}

updateProfile(
  employeeId: number,
  profileData: EditProfileRequest
): Observable<any> {
  return this.http.put<any>(
    `${this.editEmployee}/${employeeId}`,
    profileData
  );
}

}