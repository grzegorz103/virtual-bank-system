import { Injectable } from '@angular/core';
import { BankAccount } from './bank-account';
import { HttpClient } from '@angular/common/http/http';
@Injectable({
  providedIn: 'root'
})
export class BankAccountService {

  url='http://localhost:8080/api/bankAccounts';

  constructor(private http: HttpClient) { }

  findAll(){
    return this.http.get<BankAccount[]>(this.url);
  }
}
