1. Problem faced
   I wanted my Employee Management System (EMS) to display the correct dashboard and profile according to the logged-in user's role.
- Admin should navigate to the Admin dashboard.
- Employee should navigate to the Employee dashboard.
- Each user should see their own profile.
- Employees must not access Admin-only pages or APIs.


2. Problems and solutions
   Problem	Solution
   Backend returned only a JWT string.	Created LoginResponseDto containing token, role, and emailId.
   Generic type mismatch in error handling.	Identified that ApiResponse<LoginResponseDto> cannot accept a string as its data.
   Angular expected ApiResponse<string>.	Updated the response type to ApiResponse<LoginResponseDto>.
   Incorrect token storage.	Changed response.data to response.data.token.
   Every user was redirected to /admin.	Added role-based navigation: Admin → /admin; Employee → /employee.
   Role-based API access needed verification.	Checked Spring Security authorities and .hasRole("ADMIN") restrictions.
   Correct profile display was required.	Planned an authenticated /api/v1/ems/me endpoint to fetch the current user's profile.
   Protected requests need authentication.	Identified the need to verify the JWT interceptor and Authorization: Bearer <token> header.


3. Important code changes
   Backend response DTO:

   public class LoginResponseDto {
   private String token;
   private String role;
   private String emailId;
   }


Angular interfaces:

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


Angular role-based navigation:
if (response.data.role === 'ADMIN') {
this.router.navigate(['/admin']);
} else if (response.data.role === 'EMPLOYEE') {
this.router.navigate(['/employee']);
}


4. Security concepts learned
- loadUserByUsername() loads the user using their email ID.
- ROLE_ADMIN and ROLE_EMPLOYEE are authorities used by Spring Security.
- .hasRole("ADMIN") requires the ROLE_ADMIN authority.
- JWT authenticates subsequent API requests.
- Angular route guards control navigation; Spring Security enforces backend permissions.
- The /me endpoint should identify the user from the authenticated principal, not a hardcoded user ID.
5. Final testing checklist
- [ ] Backend starts successfully.
- [ ] Login response contains token, role, and email ID.
- [ ] Admin redirects to /admin.
- [ ] Employee redirects to /employee.
- [ ] JWT is stored and attached to protected requests.
- [ ] /me returns the authenticated user's profile.
- [ ] Employees are denied access to Admin-only APIs.
- [ ] Invalid credentials produce an appropriate error.