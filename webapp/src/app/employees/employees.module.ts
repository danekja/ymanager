import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {EmployeesListComponent} from './employees-list.component';
import {MatDialogModule, MatFormFieldModule, MatInputModule, MatRadioModule, MatTableModule} from '@angular/material';
import {EditEmployeeDialogComponent} from './edit-employee-dialog/edit-employee-dialog.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    EmployeesListComponent,
    EditEmployeeDialogComponent
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatDialogModule,
    MatRadioModule
  ],
  entryComponents: [
    EditEmployeeDialogComponent,
  ]
})
export class EmployeesModule { }
