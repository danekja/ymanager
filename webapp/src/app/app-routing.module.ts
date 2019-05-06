import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {EmployeesListComponent} from './employees-list/employees-list.component';
import {DashboardComponent} from './dashboard/dashboard.component';

const routes: Routes = [
  { path: 'employees', component: EmployeesListComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
