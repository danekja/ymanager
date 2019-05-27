import { NgModule } from '@angular/core';
import { UserApprovalComponent } from './user-approval.component';
import { BrowserModule } from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material';

@NgModule({
  declarations: [ UserApprovalComponent ],
  exports:      [ UserApprovalComponent ],
  imports: [BrowserModule, MatButtonModule]
})
export class UserApprovalModule { }
