import { Injectable } from '@angular/core';
import { Investment } from '../models/investment';
import { HttpClient } from '@angular/common/http';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class InvestmentService {

  url = environment.apiUrl + '/api/investments';

  constructor(private http: HttpClient) { }

  findAllByUser() {
    return this.http.get<Investment[]>(this.url + '/byUser');
  }

  create(investment: string) {
    return this.http.post<Investment>(this.url, investment);
  }

  updateStatus(id: number) {
    return this.http.patch<Investment>(this.url + '/' + id, null);
  }

  findAllActiveBySaldoId(id: any) {
    return this.http.get<Investment[]>(this.url + '/byBankAccount/' + id);
  }

}
