import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AuthorizationRequest} from '../models/requests.model';
import {LocalizationService} from '../localization/localization.service';

@Component({
  selector: 'app-user-approval',
  templateUrl: './user-approval.component.html',
  styleUrls: ['./user-approval.component.sass']
})
export class UserApprovalComponent {

  @Input() authorizationRequests: AuthorizationRequest[];
  @Output() userApprovalEvent = new EventEmitter<{requestId: number, approved: boolean}>();

  constructor(private localizationService: LocalizationService) { }

  private userApproved(reqId: number, isApproved: boolean) {
    this.userApprovalEvent.emit({requestId: reqId, approved: isApproved});
  }
}
