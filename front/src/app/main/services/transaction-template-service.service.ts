import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TransactionTemplate} from '../models/transaction-template';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TransactionTemplateService {

  url = environment.apiUrl + '/api/transactiontemplates';

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get<TransactionTemplate[]>(this.url);
  }

  findOneById(id: number) {
    return this.http.get<TransactionTemplate>(this.url + '/' + id);
  }

  create(template: TransactionTemplate) {
    return this.http.post<TransactionTemplate>(this.url, template);
  }

  update(index: any, result: any) {
    return this.http.put<TransactionTemplate>(this.url + '/' + index, result);
  }

  removeById(id: number) {
    return this.http.delete(this.url + '/' + id);
  }

  findAllByCurrentUser() {
    return this.http.get<TransactionTemplate[]>(this.url + '/users/all');
  }
}
