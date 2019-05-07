import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-add-days-off-dialog',
  templateUrl: './add-days-off-dialog.component.html',
  styleUrls: ['./add-days-off-dialog.component.sass']
})
export class AddDaysOffDialogComponent {
  MINUTE_STEP = 15;

  selectedDaysOffType: string;
  daysOffTypes: string[] = ['Sick-days', 'Extra dovolená'];

  constructor(
    public dialogRef: MatDialogRef<AddDaysOffDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddDaysOffDialogData,
    private snackBar: MatSnackBar
  ) {
    if (this.data.toDate == null) {
      this.data.toDate = this.data.fromDate;
    }
    if (this.data.fromTime == null) {
      this.data.fromTime = {hour: 8, minute: 0};
    }
    if (data.toTime == null) {
      this.data.toTime = {hour: 16, minute: 0};
    }
  }

  onConfirmClick(): void {
    // TODO lokalizace

    if (this.selectedDaysOffType == null) {
      this.snackBar.open('Nevybrán typ volna', 'Zavřít', { duration: 5000 });
    } else if (this.data.fromDate > this.data.toDate) {
      this.snackBar.open('Datum "od" nemůže být větší než "do"', 'Zavřít', { duration: 5000 });
    } else {
      // TODO API CALL
      this.dialogRef.close();
    }
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

}

export interface AddDaysOffDialogData {
  fromDate: FormControl;
  toDate: FormControl;
  fromTime: { hour: number, minute: number };
  toTime: { hour: number, minute: number };
}
