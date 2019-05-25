import { Component, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { CalendarView, CalendarEvent } from 'angular-calendar';
import {LocalizationService} from '../localization/localization.service';
import {VacationType} from '../enums/common.enum';

@Component({
  selector: 'app-day-picker',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['day-picker.component.sass'],
  templateUrl: 'day-picker.component.html'
})
export class DayPickerComponent {

  private locale;

  private vacationType = VacationType;

  private currentMonth: number;

  // Type of calendar (constant)
  view: CalendarView = CalendarView.Month;

  // Selected date for this component's purpose
  private viewDate: Date;

  // Events which are shown in calendar (title = VacationType)
  @Input() events: CalendarEvent[] = [];

  // EventEmitter informing about changes of selected date
  @Output() selectedDate = new EventEmitter<Date>();

  // EventEmitter informing about changes of selected month
  @Output() selectedMonth = new EventEmitter<number>();

  constructor(private localizationService: LocalizationService) {
    this.locale = localizationService.defaultLanguage;
    localizationService.currentLanguage
      .subscribe((data) => {
        this.locale = data;
      });

    this.viewDate = new Date();
    this.currentMonth = this.viewDate.getMonth();
    console.log(this.currentMonth);
  }

  /**
   * Method that is invoked when user clicks on a day.
   * Sets selected date and emits event informing about new selected date.
   *
   * @param date Selected date
   */
  private dayClicked({ date }: { date: Date }): void {
    this.viewDate = date;
    this.selectedDate.emit(date);
  }

  private setMonth(newMonth: number): void {
    this.currentMonth = newMonth % 12;
    if (this.currentMonth < 0) {
      this.currentMonth = 11;
    }
    this.selectedMonth.emit(this.currentMonth);
  }
}
