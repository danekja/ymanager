import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {EmployeesListComponent} from './employees-list.component';
import {MatTableModule} from '@angular/material';

@NgModule({
  declarations: [
    EmployeesListComponent,
  ],
  imports: [
    CommonModule,
    MatTableModule
  ]
})
export class EmployeesModule { }
