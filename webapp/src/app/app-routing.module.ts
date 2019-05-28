import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmployeesListComponent} from './employees/employees-list.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {EmployeeComponentGuard} from './auth/employee-component.guard';
import {DashboardComponentGuard} from './auth/dashboard-component.guard';

const routes: Routes = [
  { path: 'employees', component: EmployeesListComponent, canActivate: [EmployeeComponentGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [DashboardComponentGuard] },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
