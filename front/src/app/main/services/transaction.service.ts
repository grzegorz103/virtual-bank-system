import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../models/transaction';
import { TransactionIn } from '../models/history-element';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  url = environment.apiUrl + '/api/transaction';

  constructor(private http: HttpClient) { }

  create(transaction: string) {
    return this.http.post<Transaction>(this.url, transaction);
  }

  findAllByBankAccountId(bankAccountId: number){
    return this.http.get<TransactionIn[]>(this.url + '/byAccount/' + bankAccountId);
  }
}
