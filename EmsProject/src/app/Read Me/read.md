Angular 22 SSR - Cannot GET /viewEmployee/1 (Quick Notes)


Angular 22 SSR Refresh Issue
Revision Note
Error

Navigation worked: this.router.navigate(['/viewEmployee', id])

Refresh (F5) or direct URL showed:

Cannot GET /viewEmployee/1

Browser console:

GET http://localhost:4200/viewEmployee/1 404

Meaning: The request was failing on the Angular dev server (4200), not Spring Boot.

Root Cause

In app.routes.server.ts:

renderMode: RenderMode.Prerender

Prerender only serves pages generated at build time. Dynamic routes like /viewEmployee/1 are not pre-generated, so the server returns 404.

Solution

Change the render mode to Client.

import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: '**',
    renderMode: RenderMode.Client
  }
];

Restart the server:

ng serve

Now refreshing /viewEmployee/1 works correctly.

My Debugging Approach

Saw the error on localhost:4200 → Angular server, not Spring Boot.

Checked Angular routes → viewEmployee/:id already existed.

Noticed @angular/ssr in ng version.

Checked app.routes.server.ts.

Found RenderMode.Prerender → replaced with RenderMode.Client.

Interview Tip

If a route works on click but fails on refresh:

Port 8080 → Check backend/server routing.

Port 4200 + SSR → Check app.routes.server.ts and RenderMode.