import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Config {
  baseUrl: string;
  redirectUrl: string;

  get loginUrl(): string {
    return this.baseUrl + '/api/login/google?target=' + this.redirectUrl;
  }
}