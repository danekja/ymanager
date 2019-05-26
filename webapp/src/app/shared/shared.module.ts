import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatetimeComponent } from './datetime/datetime.component';
import {MatDatepickerModule, MatInputModule} from '@angular/material';
import {NgbTimepickerModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';

@NgModule({
  declarations: [DatetimeComponent],
  exports: [
    DatetimeComponent
  ],
  imports: [
    CommonModule,
    MatInputModule,
    MatDatepickerModule,
    NgbTimepickerModule,
    FormsModule,
    NgxMaterialTimepickerModule
  ]
})
export class SharedModule { }
