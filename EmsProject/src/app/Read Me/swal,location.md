SweetAlert2 (Swal) – Medium Notes

SweetAlert2 (Swal) is a JavaScript library that creates modern and interactive popup dialogs in Angular and other web applications. It is commonly used instead of the browser's default alert(), confirm(), and prompt() because it provides a more professional user interface.

What Swal does

Displays success, error, warning, info, and question popups.

Creates confirmation dialogs before important actions like deleting or updating data.

Supports custom titles, messages, icons, button colors, animations, and auto-close timers.

Returns the user's choice (isConfirmed, isDismissed, etc.), allowing the application to perform actions based on the user's response.

How we use it in our project

In the Employee Management System (EMS), Swal is used to:

Ask for confirmation before deleting an employee.

Show a success message after updating or deleting an employee.

Display error messages when an API request fails.

Example:

Swal.fire({
  title: 'Employee Updated!',
  text: 'The employee details have been updated successfully.',
  icon: 'success'
});


Angular Location – Medium Notes

Location is a service, provided by Angular's @angular/common package. It allows an application to interact with the browser's history and navigation without directly using the browser's window.history object.

What Location does

Navigates back to the previous page.

Moves forward in browser history.

Retrieves or changes the current URL when needed.

Provides navigation based on browser history instead of hardcoded routes.

How we use it in our project

In the EMS project, Location.back() is used after successfully updating an employee. Once the success popup is closed, the user is automatically returned to the previous page (such as the Employee List), creating a smoother user experience.

Example:

import { Location } from '@angular/common';

constructor(private location: Location) {}

this.location.back();

Using Swal for user-friendly popups and Location for browser-history navigation makes the application more interactive, professional, and easier to use.