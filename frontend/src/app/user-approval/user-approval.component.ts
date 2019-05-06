import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserToApprove } from './user-to-approve.model';


@Component({
  selector: 'app-user-approval',
  templateUrl: './user-approval.component.html',
  styleUrls: ['./user-approval.component.sass']
})
export class UserApprovalComponent {

  @Input()  usersToApprove: UserToApprove[];
  @Output() userApprovedAction = new EventEmitter<{user: UserToApprove, approved: boolean}>();

  constructor() { }

  userApproved(approvedUser: UserToApprove, isApproved: boolean) {
    this.userApprovedAction.emit({user: approvedUser, approved: isApproved});
  }

}
