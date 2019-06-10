import { NgModule } from '@angular/core';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { DayPickerComponent } from './day-picker.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material';

@NgModule({
  declarations: [ DayPickerComponent ],
  exports:      [ DayPickerComponent ],
  imports:      [
                  CalendarModule.forRoot({
                    provide: DateAdapter,
                    useFactory: adapterFactory
                  }),
                  BrowserAnimationsModule,
                  MatButtonModule
                ],
})
export class DayPickerModule {}
