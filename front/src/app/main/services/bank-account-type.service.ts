import { Injectable } from '@angular/core';
import { BankAccType } from '../models/bank-acc-type';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BankAccountTypeService {


  url = 'http://localhost:8080/api/bankaccounttypes';

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get<BankAccType[]>(this.url);
  }

  findById(id: any) {
    return this.http.get<BankAccType>(this.url + '/' + id);
  }

  update(id: any, result: any) {
    return this.http.put<BankAccType>(this.url + '/' + id, result);
  }

}
