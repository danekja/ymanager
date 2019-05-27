import { Component, EventEmitter, Output, Input } from '@angular/core';
import { VacationRequest } from '../models/requests.model';

@Component({
  selector: 'app-days-off-approval',
  templateUrl: './vacation-approval.component.html',
  styleUrls: ['./vacation-approval.component.sass']
})
export class VacationApprovalComponent {

  @Input() vacationRequests: VacationRequest[];
  @Output() vacationApprovalEvent = new EventEmitter<{requestId: number, approved: boolean}>();

  constructor() { }

  vacationApprovalCompleted(reqId: number, isApproved: boolean ) {
    this.vacationApprovalEvent.emit({requestId: reqId, approved: isApproved});
  }
}
