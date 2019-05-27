import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployerDashboardComponent } from './employer-dashboard.component';
import { DayPickerModule } from '../../day-picker/day-picker.module';
import { VacationInfoModule } from '../../vacation-info/vacation-info.module';
import { UserApprovalModule } from '../../user-approval/user-approval.module';
import { VacationApprovalModule } from '../../vacation-approval/vacation-approval.module';
import { OncomingVacationModule } from '../../oncoming-vacation/oncoming-vacation.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AddVacationDialogModule} from '../../add-vacation-dialog/add-vacation-dialog.module';
import {MatDialogModule} from '@angular/material';
import {EditVacationDialogModule} from '../../edit-vacation-dialog/edit-vacation-dialog.module';

@NgModule({
  declarations: [ EmployerDashboardComponent ],
  exports:      [ EmployerDashboardComponent ],
  imports: [
    CommonModule,
    DayPickerModule,
    VacationInfoModule,
    UserApprovalModule,
    VacationApprovalModule,
    DayPickerModule,
    OncomingVacationModule,
    BrowserAnimationsModule,
    AddVacationDialogModule,
    EditVacationDialogModule,
    MatDialogModule
  ]
})
export class EmployerDashboardModule { }
