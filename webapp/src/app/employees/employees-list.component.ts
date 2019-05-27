import {Component, OnInit} from '@angular/core';
import {UsersService} from '../services/api/users.service';
import {Languages, VacationType} from '../enums/common.enum';
import {MatDialog, MatSnackBar} from '@angular/material';
import {EditEmployeeDialogComponent} from './edit-employee-dialog/edit-employee-dialog.component';
import {DayInfo, User} from './user.model';
import {UserBasicInformation, UserProfile} from '../models/user.model';
import {DefaultSettingsDialogComponent} from './default-settings-dialog/default-settings-dialog.component';
import {Settings} from '../models/settings.model';
import {SettingsService} from '../services/api/settings.service';
import {UserService} from '../services/api/user.service';
import {LocalizationService} from '../localization/localization.service';
import {DateFormatterService} from '../services/util/date-formatter.service';
import {FileService} from '../services/api/file.service';

const daysOfWeek: string[] = [
  'po',
  'ut',
  'st',
  'ct',
  'pa',
  'so',
  'ne',
];

@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.sass']
})
export class EmployeesListComponent implements OnInit {
  days: string[];
  private _users: User[];
  private _dates: Date[];
  private _employeesBasicInformation: UserBasicInformation[] = [];
  readonly _todayDate: Date = new Date();

  constructor(
    private usersService: UsersService,
    private userService: UserService,
    private settingsService: SettingsService,
    private localizationService: LocalizationService,
    private dateFormatterService: DateFormatterService,
    private fileService: FileService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar) {
    this.generateDays();
    this.generateDates();
    this.editDates();
  }


  /**
   * Sends a request to the GET /api/export/pdf end point
   * After receiving the answer downloads the pdf file to user's download folder
   */
  downloadPdf(): void {
    // https://stackoverflow.com/a/52687792/6204336
    this.fileService.getExportedPdf()
      .subscribe((data: any) => {
        console.log(data);
        const blob = new Blob([data], {type: 'application/pdf'});
        const link = window.URL.createObjectURL(blob);
        const linkElement = document.createElement('a');
        linkElement.href = link;
        linkElement.download = 'super.pdf';
        linkElement.click();
      });
  }

  /**
   * Uploads first file from the file list to the
   * endpoint /api/import/xlsx
   * @param files file list, uses only files.item(0)
   */
  uploadXlsxFile(files: FileList): void {
    this.fileService.uploadXlsFile(files)
      .subscribe(() => this.snackBar.open('Import souboru se provedl', 'Zavřít', {duration: 5000}));
  }

  /**
   * Opens a dialog to edit users settings after closing
   * the dialog if user clicked to confirm new settings
   * new settings are sent to the PUT /api/user/settings end point
   * @param user user information
   */
  openEditUserDialog(user: User): void {
    this.userService.getUserProfile(user.id)
      .subscribe((userProfile: UserProfile) => {
        const dialogRef = this.dialog.open(EditEmployeeDialogComponent, {
          data: userProfile,
        });

        dialogRef.componentInstance.postUserSettings.subscribe((emittedData) => {
          this.userService.putUserSettings(emittedData)
            .subscribe(() => this.snackBar.open('Povedlo se', 'Zavrit', {duration: 5000}));
        });
      });
  }

  openDefaultSettingsDialog(): void {
    this.settingsService.getDefaultSettingsWithLanguage(Languages.CZECH)
      .subscribe((settingsData: Settings) => {
        const parsedDate = new Date(settingsData.notification);

        const parsedSettings = {
          notificationDate: new Date(parsedDate.getFullYear(), parsedDate.getMonth(), parsedDate.getDate()),
          notificationTime: parsedDate.getHours() + ':' + parsedDate.getMinutes(),
          sickdaysCount: settingsData.sickdayCount
        };

        this.dialog.open(DefaultSettingsDialogComponent, {
            data: parsedSettings,
            width: '300px'
          })
          .afterClosed().subscribe(data => {
            if (data && data.isConfirmed) {
              this.settingsService.postDefaultSettingsWithLanguage(this.toSettings(data), this.localizationService.getCurrentLanguage())
                .subscribe((foo: any) => console.log(foo));
            }
          });
      });
  }

  private toSettings(data): Settings {
    return {
      sickdayCount: data.sickdayCount,
      notification: this.dateFormatterService.formatDatetime(data.notificationDatetime)
    };
  }

  private generateDays(): void {
    this.days = [];

    for (let i = this._todayDate.getDay() - 8; i < this._todayDate.getDay() + 7; i++) {
      this.days.push(daysOfWeek[((i % 7) + 7) % 7]);
    }
  }

  private generateDates(): void {
    this._dates = [];
    for (let i = 0; i < 15; i++) {
      this._dates.push(new Date());
    }
  }

  private editDates(): void {
    let j = 0;
    let date: Date;

    for (let i = -7; i < 8; i++) {
      date = this._dates[j++];
      date.setDate(date.getDate() + i);
    }
  }


  /**
   * Map from UserBasicInformation model to inner class model User
   */
  private mapUsers(): void {
    let user: User;
    this._users = [];

    for (const info of this._employeesBasicInformation) {
      user = new User();
      user.name = info.firstName + ' ' + info.lastName;
      user.id = info.id;
      user.imageLink = info.photo;
      user.dates = this.mapDays(info);
      this._users.push(user);
    }
  }

  /**
   * Creates array of days with information about the user's
   * vacation and sick days in range 7 days before and 7 days after
   * @param user one user row with
   */
  private mapDays(user: UserBasicInformation): DayInfo[] {
    const dayInfo: DayInfo[] = [];

    for (const date of this._dates) {
      const day: DayInfo = new DayInfo();
      day.type = VacationType.NONE;

      for (const calendarDay of user.calendar) {
        if (new Date(calendarDay.date).getDate() === date.getDate()) {
          day.type = calendarDay.type;
        }
      }

      day.date = date;
      dayInfo.push(day);
    }

    return dayInfo;
  }

  ngOnInit() {
    this.usersService.getAuthorizedUsers()
      .subscribe((data: UserBasicInformation[]) => {
        this._employeesBasicInformation = data;
        this.mapUsers();
      });

    // const calendar: PostCalendar = { date: '1999/10/10', from: '15:00', to: '17:00', type: VacationType.VACATION };
    // this.userService.postCalendar(calendar)
    //   .subscribe((data: any) => console.log(data));

    // const settings: UserSettings = {
    //   id: 1,
    //   role: UserType.EMPLOYEE,
    //   sickdayCount: 1,
    //   vacationCount: 1,
    // };
    //
    // this.userService.putUserSettings(settings)
    //   .subscribe((data: any) => console.log(data));

  }

}
