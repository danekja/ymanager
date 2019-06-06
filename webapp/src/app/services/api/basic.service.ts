import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {throwError} from 'rxjs';
import {environment} from '../../../environments/environment';
import {MatSnackBar} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';
@Injectable({
  providedIn: 'root'
})
export class BasicService {
  protected baseUrl = environment.apiUrl;

  constructor(protected http: HttpClient, protected snackBar: MatSnackBar, protected translateService: TranslateService) { }

  protected handleError(error: HttpErrorResponse) {
    let errMsg;
    if (!error.error.error) {
      this.translateService.get('error.serverCommunication').subscribe((res: string) => {
        errMsg = res;
      });
    } else {
      errMsg = error.error.message;
    }

    this.snackBar.open(errMsg, 'X');

    return throwError(errMsg);
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
