import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import {VacationType} from '../enums/common.enum';

@Component({
  selector: 'app-add-days-off-dialog',
  templateUrl: './add-vacation-dialog.component.html',
  styleUrls: ['./add-vacation-dialog.component.sass']
})
export class AddVacationDialogComponent {
  MINUTE_STEP = 15;

  vacationType = VacationType;

  selectedVacationType: VacationType;

  constructor(
    public dialogRef: MatDialogRef<AddVacationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddVacationDialogData,
    private snackBar: MatSnackBar
  ) {
    if (this.data.toDate == null) {
      this.data.toDate = this.data.fromDate;
    }
    if (this.data.fromTime == null) {
      this.data.fromTime = '08:00';
    }
    if (data.toTime == null) {
      this.data.toTime = '16:00';
    }
  }

  onConfirmClick(): void {
    if (this.selectedVacationType == null) {
      this.snackBar.open('Nevybrán typ volna', 'Zavřít', { duration: 5000 });
    } else if (this.data.fromDate > this.data.toDate) {
      this.snackBar.open('Datum "od" nemůže být větší než "do"', 'Zavřít', { duration: 5000 });
    } else {
      this.dialogRef.close({
        isConfirmed: true,
        vacationType: this.selectedVacationType,
        fromDate: this.data.fromDate,
        fromTime: this.data.fromTime,
        toDate: this.data.toDate,
        toTime: this.data.toTime
      });
    }
  }

  onCloseClick(): void {
    this.dialogRef.close({
      isConfirmed: false
    });
  }

}

export interface AddVacationDialogData {
  fromDate: Date;
  toDate: Date;
  fromTime: string; // 'HH:mm' format
  toTime: string; // 'HH:mm' format
}
