import {Injectable} from '@angular/core';
import {Credit, CreditStatus} from '../models/credit';
import {HttpClient} from '@angular/common/http';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CreditService {

  url = environment.apiUrl + '/api/credits';

  constructor(private http: HttpClient) {
  }

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

  findById(id: any) {
    return this.http.get<Credit>(this.url + '/byId/' + id);
  }

  findAllActiveBySaldoId(id: any) {
    return this.http.get<Credit[]>(this.url + '/byBankAccount/' + id);
  }

  findAllCreditStatuses() {
    return this.http.get<CreditStatus[]>(this.url + '/statuses');
  }

  countAllByCreditType(creditType: any) {
    return this.http.get<number>(this.url + "/count?type=" + creditType);
  }
}
