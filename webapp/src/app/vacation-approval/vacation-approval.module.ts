import { NgModule } from '@angular/core';
import { VacationApprovalComponent } from './vacation-approval.component';
import { BrowserModule } from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material';

@NgModule({
  declarations: [ VacationApprovalComponent ],
  exports:      [ VacationApprovalComponent ],
  imports: [BrowserModule, MatButtonModule]
})
export class VacationApprovalModule { }
