import { Routes } from '@angular/router';
import { LoginComponent } from './login/login';
import { AdminComponent } from './admin/admin';
import { AddEmployeeComponent } from './add-employee/add-employee';
import { ManageEmployeeComponent } from './manage-employee/manage-employee';
import { ViewEmployeesComponent } from './view-employees/view-employees';
import { UpdateEmployeeComponent } from './update-employee/update-employee';
import { LeavesRequestComponent } from './leave/leave';
import { EmployeePageComponent } from './employee-page/employee-page';
import { ApplyLeaveComponent } from './apply-leave/apply-leave';
import { EditProfileComponent } from './edit-profile/edit-profile';


export const routes: Routes = [

    {
        path:'',
        component:LoginComponent
    },

    {
        path: 'login',
        component: LoginComponent
    },

    {
        path:'admin',
        component:AdminComponent
    },
    
    {
    path:'AddEmployee',
    component:AddEmployeeComponent
    },

    {
        path:'ManageEmployee',
        component:ManageEmployeeComponent
    },

    {
        path:'viewEmployee/:id',
        component:ViewEmployeesComponent
    },

    {
        path:'update-employee/:id',
        component:UpdateEmployeeComponent
    },

    {
        path: 'allLeaves',
        component: LeavesRequestComponent
    },
    
    {
        path: 'EmployeePageComponent',
        component: EmployeePageComponent
    },

    {
        path: 'applyleaves',
        component: ApplyLeaveComponent
    },

    {
        path: 'edit-profile/:id',
        component: EditProfileComponent
    }
    
];
