import {NgModule} from '@angular/core';
import {OncomingVacationComponent} from './oncoming-vacation.component';
import {BrowserModule} from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material';
import {TranslateModule} from '@ngx-translate/core';
import {CommonModule} from '@angular/common';

@NgModule({
  declarations: [OncomingVacationComponent ],
  exports: [OncomingVacationComponent ],
  imports: [BrowserModule, MatButtonModule, TranslateModule, CommonModule],
})
export class OncomingVacationModule {}
