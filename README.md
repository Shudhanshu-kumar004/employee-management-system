# Employee Management System (EMS)

## 📌 About the Project

The **Employee Management System (EMS)** is a full-stack web application developed to simplify and streamline employee management within an organization. It provides a centralized platform for managing employee information, handling employee profiles, and controlling access based on user roles.

The application uses **Angular** for the frontend, **Spring Boot** for the backend, and **MySQL** for database management. It follows a RESTful API architecture and implements secure authentication and authorization using Spring Security and JWT.

## 🚀 Key Features

- **Authentication & Authorization:** Secure login using JWT and Spring Security.
- **Role-Based Access Control:** Separate permissions for Admin and Employee roles.
- **Employee Management:** Add, view, update, search, filter, and delete employee records.
- **Employee Profile Management:** View and update employee information.
- **Leave Management:** Apply for and manage employee leave requests.
- **Resignation Management:** Handle employee resignation requests.
- **RESTful APIs:** Communication between the Angular frontend and Spring Boot backend.
- **Database Integration:** Persistent data storage using MySQL.
- **Responsive UI:** User-friendly interface built with Angular and Tailwind CSS.

## 🛠️ Technology Stack

**Frontend**
- Angular
- TypeScript
- HTML5 and CSS3
- Tailwind CSS

**Backend**
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- REST APIs
- JWT Authentication

**Database & Tools**
- MySQL
- Maven
- Git and GitHub
- Postman

## 🎯 Project Objective

The objective of this project is to build a secure, maintainable, and scalable employee management application while gaining practical experience in full-stack development, REST API integration, database design, authentication, authorization, and frontend-backend communication.

## 👨‍💻 Developer

Developed as a full-stack development project to practice and demonstrate skills in Java, Spring Boot, Angular, MySQL, and web application security.

## Netlify frontend deployment

The actual Angular workspace is `EmsProject/`; the Spring Boot project is
`My-Ems-Project/`. There is no `MyProjectEMS/` directory in this repository.

The root `netlify.toml` defines these deployment settings:

| Setting | Value |
| --- | --- |
| Base directory | `EmsProject` |
| Build command | `npm run build` |
| Publish directory (relative to base) | `dist/ems-project/browser` |
| Node.js | `24` |

Connect the repository's `main` branch and deploy it with the root configuration.
Remove any dashboard base/package-directory override that points to a different
folder. The publish directory from the repository root is
`EmsProject/dist/ems-project/browser`; do not publish the source directory, the
repository root, or the parent `dist/ems-project` directory.

The application deploys with client-side rendering (CSR). Although the source
contains Angular SSR scaffolding, `app.routes.server.ts` assigns
`RenderMode.Client` to every route. The build now generates only browser assets
and `index.html`, with no Node server requirement. The unused server source files
remain available, but SSR is not enabled in this deployment. Client hydration was
removed because no server-rendered markup is produced. The obsolete SSR serving
script was removed because this build does not produce a server bundle.

The Netlify fallback serves `/index.html` with status 200 for Angular route
requests, including parameterized routes such as `/viewEmployee/1` and
`/edit-profile/1`. Existing files take precedence, so JavaScript, CSS, and the
favicon are served directly. Angular continues to manage browser history;
all existing route definitions remain unchanged. The login page's existing
`/forgot-password` link has no matching Angular route; implementing password
recovery is separate from deployment configuration.

For local production verification, run `npm install` and `npm run build` inside
`EmsProject`. Confirm that `dist/ems-project/browser/index.html`, the hashed
JavaScript/CSS bundles, and `favicon.ico` exist. After Netlify deploys, check `/`,
`/login`, a direct request and refresh on a parameterized route, browser back and
forward navigation, and asset responses in the browser network panel. API-backed
routes also require a working backend and authenticated session.

Netlify hosts the Angular frontend only. Host Spring Boot and its existing MySQL
database separately, configure a public HTTPS API URL in the frontend in place
of the current `http://localhost:8080` endpoints, and allow the Netlify frontend
origin in the backend's CORS configuration while preserving JWT authorization.
The backend, database entities, APIs, components, styles, and routes were not
changed by this deployment fix.
