import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {
  getTestResponse;
  postTestResponse;
  enHelloWorldResponse;
  czHelloWorldResponse;
  databaseResponse;
  constructor(private httpClient: HttpClient) {}

  ngOnInit() {
    this.httpClient.get(environment.apiUrl + 'test', { responseType: 'text' })
      .subscribe(data => this.getTestResponse = data);

    this.httpClient.get(environment.apiUrl + 'test', { responseType: 'text' })
      .subscribe(data => this.postTestResponse = data);

    this.httpClient.get(environment.apiUrl + 'hello', { responseType: 'text' })
      .subscribe(data => this.enHelloWorldResponse = data);

    this.httpClient.get(environment.apiUrl + 'hello?lang=cz', { responseType: 'text' })
      .subscribe(data => this.czHelloWorldResponse = data);

    this.httpClient.get(environment.apiUrl + 'database', { responseType: 'text' })
      .subscribe(data => this.databaseResponse = data);
  }
}
