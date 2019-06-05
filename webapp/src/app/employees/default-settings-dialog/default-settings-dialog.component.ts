import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-default-settings',
  templateUrl: './default-settings-dialog.component.html',
  styleUrls: ['./default-settings.component.sass']
})
export class DefaultSettingsDialogComponent {
  MINUTE_STEP = 15;

  constructor(
    public dialogRef: MatDialogRef<DefaultSettingsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DefaultSettingsDialogData,
    private snackBar: MatSnackBar,
  ) {
  }

  onConfirmClick(): void {
    if (this.everythingFilled()) {
      this.dialogRef.close(
        {
          isConfirmed: true,
          notificationDatetime: this.toNotificationDatetime(),
          sickDayCount: this.data.sickDayCount
        }
      );
    } else {
      this.snackBar.open('Nevyplněny všechny potřebné položky');
    }
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

  private everythingFilled(): boolean {
    return this.data.notificationDate && this.data.notificationTime && this.data.sickDayCount;
  }

}

export class DefaultSettingsDialogData {
  notificationDate: Date;
  notificationTime: string;
  sickDayCount: number;
}
