import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AuthorizationRequest, Requests} from '../models/requests.model';

@Component({
  selector: 'app-user-approval',
  templateUrl: './user-approval.component.html',
  styleUrls: ['./user-approval.component.sass']
})
export class UserApprovalComponent {

  @Input() authorizationRequests: AuthorizationRequest[];
  @Output() userApprovalEvent = new EventEmitter<{requestId: number, approved: boolean}>();

  constructor() { }

  private userApproved(reqId: number, isApproved: boolean) {
    this.userApprovalEvent.emit({requestId: reqId, approved: isApproved});
  }
}
