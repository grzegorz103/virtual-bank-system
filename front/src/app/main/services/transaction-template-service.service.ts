import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http/http';

@Injectable({
  providedIn: 'root'
})
export class TransactionTemplateServiceService {

  url = 'http://localhost:8080/api/transactiontemplates';

  constructor(private http: HttpClient) { }

  findAll(){
    return this.http.get<TransactionTemplate[]>(this.url);
  }

}
