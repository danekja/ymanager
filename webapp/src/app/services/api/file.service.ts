import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {BasicService} from './basic.service';
import {catchError} from 'rxjs/operators';
import {Languages} from '../../enums/common.enum';
import {MatSnackBar} from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class FileService extends BasicService {

  constructor(protected http: HttpClient, protected snackBar: MatSnackBar) {
    super(http, snackBar);
  }

  /**
   * Uploads xlsx file which will be parsed on the server
   * @param file xlsx file to be parsed
   */
  uploadXlsFile(file: FileList) {
    return this.makeImportXlsxAPiCall(file, null);
  }

  /**
   * Uploads xlsx file which will be parsed on the server
   * @param language specify error message language
   * @param file xlsx file to be parsed
   */
  uploadXlsFileWithLanguage(file: FileList, language: Languages) {
    return this.makeImportXlsxAPiCall(file, language);
  }

  /**
   * Exports a pdf file from the imported xlsx file
   */
  getExportedPdf() {
    return this.makeExportPdfApiCall(null);
  }

  /**
   * Exports a pdf file from the imported xlsx file
   * @param language specify error message language
   */
  getExportedPdfWithLanguage(language: Languages) {
    return this.makeExportPdfApiCall(language);
  }

  /**
   * Testovaci endpoint pro export PDF souboru
   * GET /export/pdf? [lang=<CZ,EN>]
   * @param language specify error message language
   */
  private makeExportPdfApiCall(language: Languages) {
    const httpParams: HttpParams = this.createParams({lang: language});

    return this.http.get(this.baseUrl + '/api/export/pdf', {responseType: 'blob', params: httpParams})
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Testovaci endpoint pro import XLS souboru
   * POST /import/xls?[lang=<CZ,EN>]&file=<binarni_soubor>
   * @param file xlsx file to import and parse
   * @param language specify error message langauge
   */
  private makeImportXlsxAPiCall(file: FileList, language: Languages) {
    const fileCount: number = file.length;
    const formData = new FormData();

    const httpParams: HttpParams = this.createParams({lang: language});
    const options = {params: httpParams};

    if (fileCount > 0) {

      formData.append('file', file.item(0));
      return this.http.post(this.baseUrl + '/api/import/xls', formData, options);
    }
  }
}
