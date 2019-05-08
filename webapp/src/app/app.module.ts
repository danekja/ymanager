import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { EmployeesListComponent } from './employees-list/employees-list.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { HeaderComponent } from './header/header.component';
import { MatDialogModule } from '@angular/material';
import {ProfileSettingsModule} from './profile-settings/profile-settings.module';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    EmployeesListComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    DashboardModule,
    MatDialogModule,
    ProfileSettingsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
