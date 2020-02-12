import { Injectable } from '@angular/core';
import { Installment } from '../models/installment';
import { HttpClient } from '@angular/common/http';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class InstallmentService {

  url = environment.apiUrl + '/api/installments';

  constructor(private http: HttpClient) { }

  create(installment: any) {
    return this.http.post<Installment>(this.url, installment);
  }

  findAllByCreditId(creditId: number) {
    return this.http.get<Installment[]>(this.url + '/byCredit/' + creditId);
  }
}
