import { Component, EventEmitter, Output, Input } from '@angular/core';
import {Requests} from '../models/requests.model'; // TODO
import {VacationType} from '../enums/common.enum';

@Component({
  selector: 'app-days-off-approval',
  templateUrl: './days-off-approval.component.html',
  styleUrls: ['./days-off-approval.component.sass']
})
export class DaysOffApprovalComponent {

  @Input() daysOffRequests: Requests;
  @Output() daysOffApprovalEvent = new EventEmitter<{requestId: number, approved: boolean}>();

  constructor() { }

  daysOffApprovalCompleted(reqId: number, isApproved: boolean ) {
    this.daysOffApprovalEvent.emit({requestId: reqId, approved: isApproved});
  }

  private daysOffTypeToString(vacationType: VacationType): string {
    switch (vacationType) {
      case VacationType.VACATION:
        return 'Extra dovolená';
      case VacationType.SICKDAY:
        return 'Sickdays';
    }
  }
}
