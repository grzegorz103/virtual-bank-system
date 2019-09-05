import { Injectable } from '@angular/core';
import { ExchangeCurrency } from '../models/exchange-currency';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ExchangeCurrencyService {

  url = 'http://localhost:8080/api/exchangecurrency';

  constructor(private http: HttpClient) { }

  create(exchangecurrency: ExchangeCurrency){
    return this.http.post<ExchangeCurrency>(this.url, exchangecurrency);
  }
}
