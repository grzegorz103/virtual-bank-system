import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CurrencyType } from '../models/currency-type';

@Injectable({
  providedIn: 'root'
})
export class CurrencyTypeService {

  url = 'http://localhost:8080/api/currencytype';

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get<CurrencyType[]>(this.url);
  }
}
