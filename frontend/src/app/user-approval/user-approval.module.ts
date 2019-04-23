import { NgModule } from '@angular/core';
import { UserApprovalComponent } from './user-approval.component';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  declarations: [ UserApprovalComponent ],
  exports:      [ UserApprovalComponent ],
  imports:      [ BrowserModule ]
})
export class UserApprovalModule { }
