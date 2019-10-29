import {Component, OnInit} from '@angular/core';
import { Config } from '../services/util/config.service';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  loginUrl: string;

  constructor(private config: Config) {
    this.loginUrl = config.loginUrl;
  }

  ngOnInit() {
  }

}
