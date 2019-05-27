import {Component, Inject, Output} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormControl } from '@angular/forms';
import {Calendar} from '../models/calendar.model';

@Component({
  selector: 'app-edit-days-off-dialog',
  templateUrl: './edit-vacation-dialog.component.html',
  styleUrls: ['./edit-vacation-dialog.component.sass']
})
export class EditVacationDialogComponent {
  MINUTE_STEP = 15;

  constructor(
    public dialogRef: MatDialogRef<EditVacationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EditVacationDialogData,
    private snackBar: MatSnackBar
  ) {
  }

  onConfirmClick(): void {
    this.dialogRef.close({
      edited: true,
      date: this.data.vacation.date,
      from: this.data.vacation.from,
      to: this.data.vacation.to
    });
  }

  onCloseClick(): void {
    this.dialogRef.close({
        edited: false
      }
    );
  }
}

export interface EditVacationDialogData {
  vacation: Calendar;
}
