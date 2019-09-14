import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TransactionTemplate } from '../models/transaction-template';

@Injectable({
  providedIn: 'root'
})
export class TransactionTemplateService {

  url = 'http://localhost:8080/api/transactiontemplates';

  constructor(private http: HttpClient) { }

  findAll(){
    return this.http.get<TransactionTemplate[]>(this.url);
  }

  findOneById(id: number){
    return this.http.get<TransactionTemplate>(this.url + '/' + id);
  }

  create(template: TransactionTemplate){
    return this.http.post<TransactionTemplate>(this.url, template);
  }
}
