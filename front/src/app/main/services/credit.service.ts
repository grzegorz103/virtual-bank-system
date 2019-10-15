import { Injectable } from '@angular/core';
import { Credit } from '../models/credit';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CreditService {

  url = 'http://localhost:8080/api/credits';

  constructor(private http: HttpClient) { }

  create(credit: any) {
    return this.http.post<Credit>(this.url, credit);
  }

  findAllByCurrentUser() {
    return this.http.get<Credit[]>(this.url);
  }

  findAllByCreditType(creditType: string) {
    return this.http.get<Credit[]>(this.url + '/byType?creditType=' + creditType);
  }

  changeStatus(id: number, status?: string) {
    const query = this.url + '/' + id + '/status' + (status ? '?status=' + status : '');
    return this.http.patch<Credit>(query, null);
  }

  findById(id: number){
    return this.http.get<Credit>(this.url +'/byId/' + id);
  }
}
