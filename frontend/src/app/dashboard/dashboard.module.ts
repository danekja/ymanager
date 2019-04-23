import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EmployerDashboardModule} from './employer-dashboard/employer-dashboard.module';
import {DashboardComponent} from './dashboard.component';

@NgModule({
  declarations: [ DashboardComponent ],
  exports:      [ DashboardComponent ],
  imports: [
    EmployerDashboardModule
  ]
})
export class DashboardModule { }
