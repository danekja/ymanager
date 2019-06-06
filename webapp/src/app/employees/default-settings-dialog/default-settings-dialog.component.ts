import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatSnackBar} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

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
    private translateService: TranslateService
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
      this.translateService.get('error.missingField').subscribe((res: string) => {
        this.snackBar.open(res, 'X', { duration: 5000 });
      });
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
    return Boolean(this.data.notificationDate && this.data.notificationTime && this.data.sickDayCount);
  }

}

export class DefaultSettingsDialogData {
  notificationDate: Date;
  notificationTime: string;
  sickDayCount: number;
}
