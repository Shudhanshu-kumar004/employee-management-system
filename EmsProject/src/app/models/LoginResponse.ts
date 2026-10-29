export interface LoginResponse {
  id : number;
  token: string;
  role: string;
  emailId: string;
  name: string;
  designation: string;
  joiningDate: string;
  status: string;
  department: string;
  address: string;
  phoneNumber: string;
  salary: number;
  createdAt: string;
  updatedAt: string;
}

export interface ApiResponse<T> {
  data: T;
  message: string;
  status: number;
}