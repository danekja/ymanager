import { NgModule } from '@angular/core';
import { OncomingDaysOffComponent } from './oncoming-days-off.component';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  declarations: [ OncomingDaysOffComponent ],
  exports:      [ OncomingDaysOffComponent ],
  imports:      [ BrowserModule ],
})
export class OncomingDaysOffModule {}
