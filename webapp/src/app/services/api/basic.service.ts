import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {throwError} from 'rxjs';
import {environment} from '../../../environments/environment';
import {MatSnackBar} from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class BasicService {
  protected baseUrl = environment.apiUrl;

  constructor(protected http: HttpClient, protected snackBar: MatSnackBar) { }

  protected handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }

    this.snackBar.open('Komunikace se serverem se nezdařila', 'Zavřít');
    return throwError(
      'Something bad happened; please try again later.');
  }

  /**
   * Creates http parameters (query for request) for given
   * object (parameter - value), if the value is null
   * it's not added into the query
   * @param params object from which the query is created
   */
  protected createParams(params: any) {
    let httpParams = new HttpParams();
    for (const key in params) {
      if (params.hasOwnProperty(key)) {
        if (params[key] != null) {
          httpParams = httpParams.set(key, params[key]);
        }
      }
    }

    return httpParams;
  }
}
