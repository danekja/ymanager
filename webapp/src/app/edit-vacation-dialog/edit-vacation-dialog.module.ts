import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatDialogModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatInputModule, MatRadioModule, MatSnackBarModule
} from '@angular/material';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {EditVacationDialogComponent} from './edit-vacation-dialog.component';

@NgModule({
  declarations: [EditVacationDialogComponent],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
    NgbModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    BrowserModule,
    MatInputModule,
    MatRadioModule,
    MatSnackBarModule
  ],
  exports: [
    EditVacationDialogComponent
  ],
  entryComponents: [
    EditVacationDialogComponent
  ]
})
export class EditVacationDialogModule { }
