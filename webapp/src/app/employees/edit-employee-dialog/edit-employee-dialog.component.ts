import {Component, Inject, Input, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {User} from '../user.model';
import {UserType} from '../../enums/common.enum';


@Component({
  selector: 'app-edit-employee-dialog',
  templateUrl: './edit-employee-dialog.component.html',
  styleUrls: ['./edit-employee-dialog.component.sass']
})
export class EditEmployeeDialogComponent implements OnInit {
  readonly _userTypes: string[] = ['Zaměstnanec', 'Zaměstnavatel'];
  private _sickDaysCount: number;
  private _vacationDaysCount: number;
  private _userType: UserType;

  constructor(public dialogRef: MatDialogRef<EditEmployeeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: User) {

  }

  ngOnInit() {
  }

  @Input()
  set userType(userType: string) {
    this._userType = UserType.EMPLOYEE;
    console.log('intercepted ' + this._userType);
  }

  onConfirmClick(): void {
    this.dialogRef.close();
  }


  onCloseClick(): void {
    this.dialogRef.close();
  }

}
