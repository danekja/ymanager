import {Component, OnInit} from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localeCs from '@angular/common/locales/cs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {

  constructor() {
    registerLocaleData(localeCs);
  }

  ngOnInit() {}

}
