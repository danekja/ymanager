import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EmployeesListComponent} from './employees-list.component';

import {
  MatButtonModule,
  MatDatepickerModule,
  MatDialogModule,
  MatFormFieldModule,
  MatInputModule,
  MatRadioModule,
  MatTableModule
} from '@angular/material';
import {EditEmployeeDialogComponent} from './edit-employee-dialog/edit-employee-dialog.component';
import {FormsModule} from '@angular/forms';
import {DefaultSettingsDialogComponent} from './default-settings-dialog/default-settings-dialog.component';
import {NgbTimepickerModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from '../shared/shared.module';
import { UserProfileDialogComponent } from './user-profile/user-profile-dialog.component';

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
    SharedModule
  ],
  entryComponents: [
    EditEmployeeDialogComponent,
    DefaultSettingsDialogComponent,
    UserProfileDialogComponent
  ]
})
export class EmployeesModule { }
