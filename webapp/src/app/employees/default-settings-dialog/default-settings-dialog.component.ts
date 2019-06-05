import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-default-settings',
  templateUrl: './default-settings-dialog.component.html',
  styleUrls: ['./default-settings.component.sass']
})
export class DefaultSettingsDialogComponent {
  MINUTE_STEP = 15;

  constructor(
    public dialogRef: MatDialogRef<DefaultSettingsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DefaultSettingsDialogData
  ) {
  }

  onConfirmClick(): void {
    this.dialogRef.close({
      isConfirmed: true,
      notificationDatetime: this.toNotificationDatetime(),
      sickDayCount: this.data.sickDayCount
    });
  }

  onCloseClick(): void {
    this.dialogRef.close({
      isConfirmed: false
    });
  }

  private toNotificationDatetime(): Date {
    const splittedTime = this.data.notificationTime.split(':');

    return new Date(
      this.data.notificationDate.getFullYear(),
      this.data.notificationDate.getMonth(),
      this.data.notificationDate.getDate(),
      Number(splittedTime[0]),
      Number(splittedTime[1])
    );
  }

}

export class DefaultSettingsDialogData {
  notificationDate: Date;
  notificationTime: string;
  sickDayCount: number;
}
