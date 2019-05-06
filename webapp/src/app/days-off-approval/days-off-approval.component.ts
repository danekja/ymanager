import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DaysOff } from '../shared/days-off.model';
import { OffDayType } from '../shared/off-day-type';

@Component({
  selector: 'app-days-off-approval',
  templateUrl: './days-off-approval.component.html',
  styleUrls: ['./days-off-approval.component.sass']
})
export class DaysOffApprovalComponent implements OnInit {

  @Input()  daysOffToApprove: DaysOff[];
  @Output() daysOffApprovalAction = new EventEmitter<{daysOff: DaysOff, approved: boolean}>();

  constructor() { }

  ngOnInit() {
  }

  daysOffApprovalCompleted(daysOffApproved: DaysOff, isApproved: boolean ) {
    this.daysOffApprovalAction.emit({daysOff: daysOffApproved, approved: isApproved});
  }

  private daysOffTypeToString(taskType: OffDayType): string {
    switch (taskType) {
      case OffDayType.ExtraVacation:
        return 'Extra dovolená';
      case OffDayType.Sickday:
        return 'Sickdays';
    }
  }
}
