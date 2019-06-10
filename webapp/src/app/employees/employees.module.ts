import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EmployeesListComponent} from './employees-list.component';
import {TranslateModule} from '@ngx-translate/core';

import {
  MatButtonModule,
  MatDatepickerModule,
  MatDialogModule,
  MatFormFieldModule,
  MatInputModule,
  MatRadioModule,
  MatTableModule,

} from '@angular/material';
import {EditEmployeeDialogComponent} from './edit-employee-dialog/edit-employee-dialog.component';
import {FormsModule} from '@angular/forms';
import {DefaultSettingsDialogComponent} from './default-settings-dialog/default-settings-dialog.component';
import {NgbTimepickerModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from '../shared/shared.module';
import { UserProfileDialogComponent } from './user-profile/user-profile-dialog.component';
import {DayPickerModule} from '../shared/day-picker/day-picker.module';
import {VacationInfoModule} from '../shared/vacation-info/vacation-info.module';

@NgModule({
  declarations: [
    EmployeesListComponent,
    EditEmployeeDialogComponent,
    DefaultSettingsDialogComponent,
    UserProfileDialogComponent
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatDialogModule,
    MatRadioModule,
    MatDatepickerModule,
    NgbTimepickerModule,
    MatButtonModule,
    DayPickerModule,
    SharedModule,
    VacationInfoModule,
    TranslateModule,
  ],
  entryComponents: [
    EditEmployeeDialogComponent,
    DefaultSettingsDialogComponent,
    UserProfileDialogComponent
  ]
})
export class EmployeesModule { }
