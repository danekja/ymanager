import { NgModule } from '@angular/core';
import { DaysOffApprovalComponent } from './days-off-approval.component';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  declarations: [ DaysOffApprovalComponent ],
  exports:      [ DaysOffApprovalComponent ],
  imports:      [ BrowserModule ]
})
export class DaysOffApprovalModule { }
