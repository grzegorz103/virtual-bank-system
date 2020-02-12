import {Injectable} from '@angular/core';
import {ExchangeCurrency} from '../models/exchange-currency';
import {HttpClient} from '@angular/common/http';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ExchangeCurrencyService {

  url = environment.apiUrl + '/api/exchangecurrency';

  constructor(private http: HttpClient) {
  }

  create(exchangecurrency: ExchangeCurrency) {
    return this.http.post<ExchangeCurrency>(this.url, exchangecurrency);
  }

  calculate(exchangeCurrency: ExchangeCurrency) {
    return this.http.post<string>(this.url + '/calculate', exchangeCurrency);
  }
}
