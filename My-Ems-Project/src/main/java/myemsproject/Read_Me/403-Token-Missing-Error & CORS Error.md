Problem 1: 403 Forbidden — JWT Token Missing

Problem: The application worked correctly in Postman when I manually added the JWT token, but the Angular browser displayed 403 Forbidden. All employee records and dashboard counts showed 0.

Root cause: The browser requests did not contain the Authorization header. My Spring Boot JwtFilter expected a JWT in the request header, but Angular was not sending it. 

Solution:
1. Updated the Angular login component to save the JWT returned by the backend.
2. Stored the token using localStorage.setItem('token', response.data).
3. Created an Angular HTTP interceptor to attach the token automatically.
4. Registered the interceptor using provideHttpClient(withInterceptors([authInterceptor])) in app.config.ts.
   Example:
   const token = localStorage.getItem('token');

   
                 const authReq = req.clone({
                 setHeaders: {
                 Authorization: `Bearer ${token}`
                 }
                 });

Result: Angular could send the JWT with protected API requests. This addressed the missing-token problem, although a 403 can also result from insufficient authorities.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

Problem 2: CORS Error — Cross-Origin Requests Blocked

Problem: After addressing the missing JWT header, the login API returned 200 OK, but dashboard APIs such as count, employee, leaves, and request showed CORS errors. The dashboard still displayed zeros.
Root cause: Angular was running on http://localhost:4200, while Spring Boot was running on http://localhost:8080. Different ports mean different origins, so the browser required appropriate CORS permission from the backend.
Solution:
1. Added a CorsConfigurationSource bean in the Spring Security configuration.
2. Allowed the Angular origin, http://localhost:4200.
3. Allowed the required HTTP methods: GET, POST, PUT, PATCH, DELETE, and OPTIONS.
4. Allowed headers such as Authorization and Content-Type.
5. Enabled CORS in the security chain using .cors(Customizer.withDefaults()).
6. Restarted Spring Boot and tested the API requests again.

   Example:

   http
   .cors(Customizer.withDefaults())
   .csrf(csrf -> csrf.disable());


Result: The backend was configured to allow requests from Angular. The remaining verification was to confirm that the APIs returned 200 OK and actual data.