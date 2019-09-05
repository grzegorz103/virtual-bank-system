import { Injectable } from '@angular/core';
import { BankAccount } from '../models/bank-account';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class BankAccountService {

  url='http://localhost:8080/api/bankaccount';

  constructor(private http: HttpClient) { }

  findAll(){
    return this.http.get<BankAccount[]>(this.url);
  }
}
