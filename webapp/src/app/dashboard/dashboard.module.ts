import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DayPickerModule } from '../shared/day-picker/day-picker.module';
import { VacationInfoModule } from '../shared/vacation-info/vacation-info.module';
import { UserApprovalModule } from './user-approval/user-approval.module';
import { VacationApprovalModule } from './vacation-approval/vacation-approval.module';
import { OncomingVacationModule } from './oncoming-vacation/oncoming-vacation.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AddVacationDialogModule} from './add-vacation-dialog/add-vacation-dialog.module';
import {MatDialogModule} from '@angular/material';
import {DashboardComponent} from './dashboard.component';

@NgModule({
  declarations: [ DashboardComponent ],
  exports:      [ DashboardComponent ],
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
    MatDialogModule
  ]
})
export class DashboardModule { }
