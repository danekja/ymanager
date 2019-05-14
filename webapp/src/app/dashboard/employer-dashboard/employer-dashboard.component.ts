import {Component, Input, OnInit} from '@angular/core';
import { MatDialog } from '@angular/material';
import { AddDaysOffDialogComponent } from '../../add-days-off-dialog/add-days-off-dialog.component';
import {UsersService} from '../../services/users.service';
import {Requests} from '../../models/requests.model';
import {UserProfile} from '../../models/user-profile.model';
import {UserService} from '../../services/user.service';
import {ProfileService} from '../../services/profile.service';
import {TimeUnit} from '../../enums/common.enum';

@Component({
  selector: 'app-employer-dashboard',
  templateUrl: './employer-dashboard.component.html',
  styleUrls: ['./employer-dashboard.component.sass']
})
export class EmployerDashboardComponent implements OnInit {

  @Input() profile: UserProfile;
  private authorizationRequests: Requests;
  private daysOffRequests: Requests;

  constructor(
    public dialog: MatDialog,
    private profileService: ProfileService,
    // API
    private userService: UserService,
    private usersService: UsersService
  ) { }

  ngOnInit() {
    // this.profileService.getProfile()
    //   .subscribe((data: UserProfile) => this.profile = data);
    //
    // this.usersService.getAuthorizationRequests()
    //   .subscribe((data: Requests) => this.authorizationRequests = data);
    //
    // this.usersService.getVacationRequests()
    //   .subscribe((data: Requests) => this.daysOffRequests = data);

    // Tmp mock
    this.profile = {
      id: 1,
      name: {
        first: 'Jon',
        last: 'Doe',
      },
      photo: 'http://mosaddek.com/theme/diverse/assets/img/user1.png',
      settings: {
        notification: new Date(2019, 3, 25, 18, 0)
      },
      vacation: {
        value: 5,
        unit: TimeUnit.DAY,
      },
      sickDay: {
        value: 10,
        unit: TimeUnit.DAY
      }
    };
  }

  private userApproved(requestId: number, approved: boolean) {
    // TODO api post call
    this.authorizationRequests.authorization.splice(0, 1);
  }

  private daysOffApproved(requestId: number, approved: boolean) {
    // TODO api post call
    this.daysOffRequests.vacation.splice(0, 1);
  }

  onDateSelect( date: Date ) {
    this.dialog.open(AddDaysOffDialogComponent, {
      data: {
        fromDate: date
      }
    });
  }
}
