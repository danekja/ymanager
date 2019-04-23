import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployerDashboardComponent } from './employer-dashboard.component';
import { DayPickerModule } from '../../day-picker/day-picker.module';
import { DaysOffInfoModule } from '../../days-off-info/days-off-info.module';
import { UserApprovalModule } from '../../user-approval/user-approval.module';
import { DaysOffApprovalModule } from '../../days-off-approval/days-off-approval.module';
import { OncomingDaysOffModule } from '../../oncoming-days-off/oncoming-days-off.module';

@NgModule({
  declarations: [ EmployerDashboardComponent ],
  exports:      [ EmployerDashboardComponent ],
  imports: [
    CommonModule,
    DayPickerModule,
    DaysOffInfoModule,
    UserApprovalModule,
    DaysOffApprovalModule,
    DayPickerModule,
    OncomingDaysOffModule
  ]
})
export class EmployerDashboardModule { }
