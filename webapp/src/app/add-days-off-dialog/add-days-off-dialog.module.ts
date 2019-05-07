import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddDaysOffDialogComponent } from './add-days-off-dialog.component';
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

@NgModule({
  declarations: [AddDaysOffDialogComponent],
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
    AddDaysOffDialogComponent
  ],
  entryComponents: [
    AddDaysOffDialogComponent
  ]
})
export class AddDaysOffDialogModule { }
