import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatSnackBar} from '@angular/material';
import {UserType} from '../../enums/common.enum';
import {UserProfile} from '../../models/user.model';
import {UserSettings} from '../../models/settings.model';


@Component({
  selector: 'app-edit-employee-dialog',
  templateUrl: './edit-employee-dialog.component.html',
  styleUrls: ['./edit-employee-dialog.component.sass']
})
export class EditEmployeeDialogComponent implements OnInit {
  readonly _userTypes: string[] = ['EMPLOYER', 'EMPLOYEE'];
  private _sickDaysCount: number;
  private _vacationDaysCount: number;
  private _userType: UserType;
  private readonly _userId: number;
  @Output() postUserSettings = new EventEmitter<UserSettings>();

  constructor(public dialogRef: MatDialogRef<EditEmployeeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: UserProfile,
              private snackBar: MatSnackBar) {
    this._sickDaysCount = data.sickDayCount;
    this._vacationDaysCount = data.vacationCount;
    this._userType = data.role;
    this._userId = data.id;
  }

  ngOnInit() {
  }

  onConfirmClick(): void {
    if (this._sickDaysCount == null || this._vacationDaysCount == null || this._userType == null) {
      this.snackBar.open('Vyplňte prosím všechny údaje', 'Zavřít');
    } else {
      this.postUserSettings.emit({
        id: this._userId,
        role: this._userType,
        sickDayCount: this._sickDaysCount,
        vacationCount: this._vacationDaysCount
      });

      this.dialogRef.close();
    }
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

}
