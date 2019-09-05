import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../models/transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  url = 'http://localhost:8080/api/transaction';

  constructor(private http: HttpClient) { }

  create(transaction: Transaction) {
    return this.http.post<Transaction>(this.url, transaction);
  }

}
