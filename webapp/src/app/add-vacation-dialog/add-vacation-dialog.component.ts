import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatSnackBar} from '@angular/material';
import {VacationType} from '../enums/common.enum';
import {FormControl} from '@angular/forms';
import {DateFormatterService} from '../services/util/date-formatter.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-add-days-off-dialog',
  templateUrl: './add-vacation-dialog.component.html',
  styleUrls: ['./add-vacation-dialog.component.sass']
})
export class AddVacationDialogComponent {
  MINUTE_STEP = 15;

  vacationType = VacationType;

  selectedVacationType: VacationType;

  dateFormControl: FormControl = new FormControl();

  constructor(
    public dialogRef: MatDialogRef<AddVacationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddVacationDialogData,
    private snackBar: MatSnackBar,
    private dateFormatterService: DateFormatterService,
    private translateService: TranslateService
  ) {
    if (this.data.fromTime == null) {
      this.data.fromTime = '08:00';
    }
    if (data.toTime == null) {
      this.data.toTime = '16:00';
    }

    this.dateFormControl.setValue(data.date);
  }

  onConfirmClick(): void {
    if (this.selectedVacationType == null) {
      this.translateService.get('error.vacationTypeNotSelected').subscribe((res: string) => {
        this.snackBar.open(res, 'X', { duration: 5000 });
      });
    } else {
      let data;
      const formattedDate = this.dateFormatterService.formatDate(this.data.date);

      if (this.selectedVacationType === VacationType.VACATION) {
        data = {
          isConfirmed: true,
          vacationType: this.selectedVacationType,
          date: formattedDate,
          fromTime: this.data.fromTime,
          toTime: this.data.toTime
        };
      } else {
        data = {
          isConfirmed: true,
          vacationType: this.selectedVacationType,
          date: formattedDate
        };
      }

      this.dialogRef.close(data);
    }
  }

  onCloseClick(): void {
    this.dialogRef.close({
      isConfirmed: false
    });
  }

}

export interface AddVacationDialogData {
  date: Date;
  fromTime: string; // 'HH:mm' format
  toTime: string; // 'HH:mm' format
}
