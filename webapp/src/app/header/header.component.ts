import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ProfileSettingsComponent } from '../profile-settings/profile-settings.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent {
  @Input() name = 'John Doe';

  constructor(private dialog: MatDialog) { }

  onProfileClick(): void {
    this.dialog.open(ProfileSettingsComponent, {
      data: {
      }
    });
  }
}
