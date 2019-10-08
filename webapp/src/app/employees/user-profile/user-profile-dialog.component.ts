import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {UserProfile} from '../../models/user.model';
import {Calendar} from '../../models/calendar.model';
import {RequestStatus} from '../../enums/common.enum';
import {UserService} from '../../services/api/user.service';
import {DateToolsService} from '../../services/util/date-tools.service';
import {LocalizationService} from '../../localization/localization.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile-dialog.component.html',
  styleUrls: ['./user-profile-dialog.component.sass']
})
export class UserProfileDialogComponent implements OnInit, AfterViewInit {
  @ViewChild('dayPicker', {static: false}) calendar;

  private profile: UserProfile;

  constructor(
    public dialogRef: MatDialogRef<UserProfileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserProfileDialogData,
    private userService: UserService,
    private dateToolsService: DateToolsService,
    private localizationService: LocalizationService
  ) { }

  ngOnInit() {

  }


  ngAfterViewInit(): void {
    this.loadProfile();
    this.loadMonthVacation(this.dateToolsService.toStartOfMonth(new Date()));
  }

  onSelectedMonthChange(monthStart: Date) {
    this.loadMonthVacation(monthStart);
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

  private loadProfile() {
    this.userService.getUserProfile(this.data.userId)
      .subscribe((data: UserProfile) => this.profile = data);
  }

  private loadMonthVacation(month: Date) {
    const fromDate = this.dateToolsService.toStartOfMonth(month);
    const toDate = this.dateToolsService.toEndOfMonth(fromDate);

    this.userService.getUserCalendarWithOptions(String(this.data.userId), fromDate, toDate, this.localizationService.getCurrentLanguage(), RequestStatus.ACCEPTED)
      .subscribe((data: Calendar[]) => {
        if (data) {
          this.calendar.setVacation(data);
        }
      });
  }
}

export class UserProfileDialogData {
  userId: number;
}
