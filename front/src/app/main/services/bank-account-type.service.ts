import { Injectable } from '@angular/core';
import { BankAccType } from '../models/bank-acc-type';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BankAccountTypeService {

  url = 'http://localhost:8080/bankaccounttypes';

  constructor(private http: HttpClient) { }

  findAll(){
    return this.http.get<BankAccType[]>(this.url);
  }
}
