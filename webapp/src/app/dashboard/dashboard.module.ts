import {NgModule} from '@angular/core';
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
