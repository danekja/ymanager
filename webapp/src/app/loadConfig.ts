import { HttpClient } from '@angular/common/http';
import { Config } from './services/util/config.service';

export function loadConfig(http: HttpClient, config: Config) {
  return (): Promise<void> => {
    const configFile = 'assets/config/config.json';

    return new Promise<void>((resolve, reject) => {
      http.get(configFile).toPromise().then((response: any) => {
        config.baseUrl = response.baseUrl;
        config.redirectUrl = response.redirectUrl;
        resolve();
      });
    });
  };
}