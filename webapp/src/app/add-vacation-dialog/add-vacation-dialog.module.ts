import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddVacationDialogComponent } from './add-vacation-dialog.component';
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
import {SharedModule} from '../shared/shared.module';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import {TranslateModule} from '@ngx-translate/core';


@NgModule({
  declarations: [AddVacationDialogComponent],
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
    MatSnackBarModule,
    SharedModule,
    NgxMaterialTimepickerModule,
    TranslateModule
  ],
  exports: [
    AddVacationDialogComponent
  ],
  entryComponents: [
    AddVacationDialogComponent
  ]
})
export class AddVacationDialogModule { }
