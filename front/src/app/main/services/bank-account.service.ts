import { Injectable } from '@angular/core';
import { BankAccount } from '../models/bank-account';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class BankAccountService {
 
  url = 'http://localhost:8080/api/bankaccount';

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get<BankAccount[]>(this.url);
  }

  findById(id: number) {
    return this.http.get<BankAccount>(this.url + '/' + id);
  }

  getCountByBankAccountTypeId(bankAccountTypeId: number) {
    return this.http.get<number>(this.url + '/' + bankAccountTypeId + '/accountCount');
  }

  findByUser() {
    return this.http.get<BankAccount[]>(this.url + '/byUser');
  }

  create(bankAccount: BankAccount) {
    return this.http.post<BankAccount>(this.url, bankAccount);
  }

  deleteById(id: any){
    return this.http.delete(this.url + '/' + id);
  }
}
