import { NgModule } from '@angular/core';
import { UserApprovalComponent } from './user-approval.component';
import { BrowserModule } from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material';
import {TranslateModule} from '@ngx-translate/core';
import {CommonModule} from '@angular/common';

@NgModule({
  declarations: [ UserApprovalComponent ],
  exports:      [ UserApprovalComponent ],
  imports: [BrowserModule, MatButtonModule, TranslateModule, CommonModule]
})
export class UserApprovalModule { }
