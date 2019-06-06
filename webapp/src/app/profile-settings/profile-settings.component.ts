import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import {DateToolsService} from '../services/util/date-tools.service';
import {DateFormatterService} from '../services/util/date-formatter.service';

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.component.html',
  styleUrls: ['./profile-settings.component.sass']
})
export class ProfileSettingsComponent {
  private date: Date;
  private time: string;

  constructor(
    private dateToolsService: DateToolsService,
    private dateFormatterService: DateFormatterService,
    public dialogRef: MatDialogRef<ProfileSettingsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProfileSettingsDialogData,
  ) {
    const parsedDatetime = this.dateToolsService.toDateAndTime(this.data.notification);

    this.date = parsedDatetime.date;
    this.time = parsedDatetime.time;
  }

  onConfirmClick(): void {
    this.dialogRef.close({
      isConfirmed: true,
      notification: this.dateFormatterService.formatDatetime(
        this.dateToolsService.toDate(
          this.dateFormatterService.formatDate(this.date),
          this.time
        )
      )
    });
  }

  onCloseClick(): void {
    this.dialogRef.close({
      isConfirmed: false
    });
  }
}

export interface ProfileSettingsDialogData {
  notification: string; // yyyy/mm/dd hh:mm:ss
}
