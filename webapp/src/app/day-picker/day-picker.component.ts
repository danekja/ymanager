import {ChangeDetectionStrategy, Component, EventEmitter, Output} from '@angular/core';
import {CalendarEvent, CalendarView} from 'angular-calendar';
import {LocalizationService} from '../localization/localization.service';
import {VacationType} from '../enums/common.enum';
import {Calendar} from '../models/calendar.model';
import {DateToolsService} from '../services/util/date-tools.service';
import {Subject} from 'rxjs';

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

  private currentYear: number;

  // Type of calendar (constant)
  view: CalendarView = CalendarView.Month;

  // Selected date for this component's purpose
  private viewDate: Date;

  // Events which are shown in calendar (title = VacationType)
  private events: CalendarEvent[] = [];

  // EventEmitter informing about changes of selected date
  @Output() selectedDate = new EventEmitter<Date>();

  // EventEmitter informing about changes of selected month (emits start of the selected month)
  @Output() selectedMonth = new EventEmitter<Date>();

  private refresh: Subject<any> = new Subject();

  constructor(
    private localizationService: LocalizationService,
    private dateToolsService: DateToolsService) {

    this.locale = localizationService.getCurrentLocale();
    localizationService.currentLanguageSubject
      .subscribe((data) => {
        this.locale = data;
      });

    this.viewDate = new Date();
    this.currentMonth = this.viewDate.getMonth();
    this.currentYear = this.viewDate.getFullYear();

    // Calendar component needs to be refreshed on language switch or else language switch doesn't have an effect
    this.localizationService.currentLanguageSubject.subscribe(e => this.refresh.next());
  }

  public setVacation(vacations: Calendar[]) {
    this.events = [];

    for (const vac of vacations) {
      this.events.push({
        start: this.dateToolsService.toDate(vac.date, vac.from),
        end: this.dateToolsService.toDate(vac.date, vac.to),
        title: vac.type
      });
    }

    this.refresh.next();
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
    if (newMonth < 0) {
      this.currentMonth = 11;
      this.currentYear--;
    } else if (newMonth > 11) {
      this.currentMonth = 0;
      this.currentYear++;
    } else {
      this.currentMonth = newMonth;
    }

    this.selectedMonth.emit(new Date(this.currentYear, this.currentMonth, 1, 0, 0, 0));
  }
}
