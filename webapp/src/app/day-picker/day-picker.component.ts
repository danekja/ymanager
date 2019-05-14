import { Component, ChangeDetectionStrategy, Output, EventEmitter } from '@angular/core';
import { CalendarView } from 'angular-calendar';
import {LocalizationService} from '../localization/localization.service';

@Component({
  selector: 'app-day-picker',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['day-picker.component.sass'],
  templateUrl: 'day-picker.component.html'
})
export class DayPickerComponent {

  private locale;

  // Type of calendar (constant)
  view: CalendarView = CalendarView.Month;

  // Selected date for this component's purpose
  private viewDate: Date = new Date();

  // EventEmitter informing about changes of selected date
  @Output() selectedDate = new EventEmitter<Date>();

  constructor(private localizationService: LocalizationService) {
    this.locale = localizationService.defaultLanguage;
    localizationService.currentLanguage
      .subscribe((data) => {
        this.locale = data;
      });
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
}
