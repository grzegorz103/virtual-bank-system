import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CurrencyType } from '../models/currency-type';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CurrencyTypeService {

  url = environment.apiUrl +'/api/currencytype';

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get<CurrencyType[]>(this.url);
  }

  findById(id: any){
    return this.http.get<CurrencyType>(this.url + '/' + id);
  }

  update(id: any, currencyType: CurrencyType){
    return this.http.put<CurrencyType>(this.url + '/' + id, currencyType);
  }
}
