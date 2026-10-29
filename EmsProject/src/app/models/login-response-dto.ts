export interface LoginResponseDto {
  token: string;
  role: 'ADMIN' | 'EMPLOYEE';
  emailId: string;
}

export interface ApiResponse<T> {
  message: string;
  status: number;
  data: T;
}