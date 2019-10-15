import { Injectable } from '@angular/core';
import { Installment } from '../models/installment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InstallmentService {

  url = 'http://localhost:8080/api/installments';

  constructor(private http: HttpClient) { }

  create(installment: string) {
    return this.http.post<Installment>(this.url, installment);
  }

  findAllByCreditId(creditId: number) {
    return this.http.get<Installment[]>(this.url);
  }
}
