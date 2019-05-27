import { NgModule } from '@angular/core';
import { OncomingVacationComponent } from './oncoming-vacation.component';
import { BrowserModule } from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material';

@NgModule({
  declarations: [ OncomingVacationComponent ],
  exports:      [ OncomingVacationComponent ],
  imports:      [ BrowserModule, MatButtonModule ],
})
export class OncomingVacationModule {}
