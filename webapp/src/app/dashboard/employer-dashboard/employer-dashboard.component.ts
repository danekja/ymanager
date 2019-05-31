import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material';
import {AddVacationDialogComponent} from '../../add-vacation-dialog/add-vacation-dialog.component';
import {UsersService} from '../../services/api/users.service';
import {AuthorizationRequest, VacationRequest} from '../../models/requests.model';
import {UserService} from '../../services/api/user.service';
import {UserProfile} from '../../models/user.model';
import {LocalizationService} from '../../localization/localization.service';
import {RequestStatus, RequestTypes, UserType} from '../../enums/common.enum';
import {Calendar} from '../../models/calendar.model';
import {DateToolsService} from '../../services/util/date-tools.service';

@Component({
  selector: 'app-employer-dashboard',
  templateUrl: './employer-dashboard.component.html',
  styleUrls: ['./employer-dashboard.component.sass']
})
export class EmployerDashboardComponent implements OnInit {
  @ViewChild('dayPicker') calendar;

  private profile: UserProfile;
  private authorizationRequests: AuthorizationRequest[];
  private vacationRequests: VacationRequest[];
  private oncomingVacation: Calendar[];

  private selectedMonth: Date;

  constructor(
    public dialog: MatDialog,
    private localizationService: LocalizationService,
    private dateToolsService: DateToolsService,
    private userService: UserService,
    private usersService: UsersService
  ) { }

  ngOnInit() {
    this.selectedMonth = this.dateToolsService.toStartOfMonth(new Date());

    this.userService.getLoggedUserProfile()
      .subscribe((data: UserProfile) => {
        this.profile = data;
        if (this.isEmployer()) {
          this.loadAuthorizationRequests();
          this.loadVacationRequests();
        }
      });

    this.loadMonthVacation(this.selectedMonth);
    this.loadOncomingVacation();
  }

  userApproved(requestId: number, approved: boolean) {
    this.requestApproved(requestId, RequestTypes.AUTHORIZATION, approved)
      .subscribe(() => this.loadAuthorizationRequests());
  }

  vacationApproved(requestId: number, approved: boolean) {
    this.requestApproved(requestId, RequestTypes.VACATION, approved)
      .subscribe(() => this.loadVacationRequests());
  }

  requestApproved(requestId: number, requestType: RequestTypes, approved: boolean) {
    const request = {
      id: requestId,
      status: approved ? RequestStatus.ACCEPTED : RequestStatus.REJECTED
    };

    return this.userService.putUserRequestWithLanguage(request, requestType, this.localizationService.getCurrentLanguage());
  }

  removeVacation(vac: Calendar) {
    this.userService.deleteCalendar(vac.id, this.localizationService.getCurrentLanguage())
      .subscribe(() => {
        this.loadOncomingVacation();
        this.loadMonthVacation(this.selectedMonth);
        this.loadProfile();
      });
  }

  onDateSelect( date: Date ) {
    this.dialog
      .open(AddVacationDialogComponent, {
        data: {
          date
        }
      })
      .afterClosed().subscribe(data => {
        if (data && data.isConfirmed) {
          this.userService.postCalendarWithLanguage(
            {
              date: data.date,
              from: data.fromTime,
              to: data.toTime,
              type: data.vacationType
            },
            this.localizationService.getCurrentLanguage()
          ).subscribe(() => {
            this.loadMonthVacation(this.selectedMonth);
            this.loadOncomingVacation();
            this.loadProfile();
          });
        }
      });
  }

  onSelectedMonthChange(monthStart: Date) {
    this.selectedMonth = monthStart;
    this.loadMonthVacation(monthStart);
  }

  isEmployer(): boolean {
    if (this.profile) {
      return this.profile.role === UserType.EMPLOYER;
    } else {
      return false;
    }
  }

  private loadProfile() {
    this.userService.getLoggedUserProfile()
      .subscribe((data: UserProfile) => this.profile = data);
  }

  private loadAuthorizationRequests() {
    this.usersService.getAuthorizationRequestsWithLanguage(this.localizationService.getCurrentLanguage(), RequestStatus.PENDING)
      .subscribe((data: AuthorizationRequest[]) => this.authorizationRequests = data);
  }

  private loadVacationRequests() {
    this.usersService.getVacationRequestsWithLanguage(this.localizationService.getCurrentLanguage(), RequestStatus.PENDING)
      .subscribe((data: VacationRequest[]) => this.vacationRequests = data);
  }

  private loadMonthVacation(month: Date) {
    const fromDate = this.dateToolsService.toStartOfMonth(month);
    const toDate = this.dateToolsService.toEndOfMonth(fromDate);

    this.userService.getLoggedUserCalendarWithOptions(fromDate, toDate, this.localizationService.getCurrentLanguage(), RequestStatus.ACCEPTED)
      .subscribe((data: Calendar[]) => {
        if (data) {
          this.calendar.setVacation(data);
        }
      });
  }

  private loadOncomingVacation() {
    const fromDate = new Date();

    this.userService.getLoggedUserCalendarWithOptions(fromDate, null, this.localizationService.getCurrentLanguage(), null)
      .subscribe((data: Calendar[]) => this.oncomingVacation = data);
  }
}
