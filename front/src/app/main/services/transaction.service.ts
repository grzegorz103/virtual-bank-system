import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../models/transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  url = 'http://localhost:8080/api/transaction';

  constructor(private http: HttpClient) { }

  create(transaction: string) {
    return this.http.post<Transaction>(this.url, transaction);
  }

  findAllByBankAccountId(bankAccountId: number){
    return this.http.get<Transaction[]>(this.url);
  }
}
