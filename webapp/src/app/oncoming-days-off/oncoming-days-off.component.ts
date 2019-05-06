import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DaysOff} from '../shared/days-off.model';
import {OffDayType} from '../shared/off-day-type';

@Component({
  selector: 'app-coming-days-off',
  templateUrl: './oncoming-days-off.component.html',
  styleUrls: ['./oncoming-days-off.component.sass']
})
export class OncomingDaysOffComponent implements OnInit {

  @Input()  oncomingDaysOff: DaysOff[];
  @Output() daysOffRemovedAction = new EventEmitter<{daysOff: DaysOff}>();

  constructor() { }

  ngOnInit() {
  }

  private daysOffRemoved( removedDaysOff: DaysOff ) {
    this.daysOffRemovedAction.emit( {daysOff: removedDaysOff } );
  }

  // TODO
  //  days-off-approval duplicate
  private offDayTypeToString(taskType: OffDayType): string {
    switch (taskType) {
      case OffDayType.ExtraVacation:
        return 'Extra dovolená';
      case OffDayType.Sickday:
        return 'Sickdays';
    }
  }
}
