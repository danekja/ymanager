import {NgModule} from '@angular/core';
import {VacationApprovalComponent} from './vacation-approval.component';
import {BrowserModule} from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material';
import {TranslateModule} from '@ngx-translate/core';
import {CommonModule} from '@angular/common';

@NgModule({
  declarations: [VacationApprovalComponent],
  exports: [VacationApprovalComponent],
  imports: [BrowserModule, MatButtonModule, TranslateModule, CommonModule]
})
export class VacationApprovalModule {
}
