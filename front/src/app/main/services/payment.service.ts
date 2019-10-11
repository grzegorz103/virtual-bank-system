import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Payment } from '../models/payment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  url = 'http://localhost:8080/api/payments';

  constructor(private http: HttpClient) { }

  create(payment: string){
    return this.http.post<Payment>(this.url, payment);
  }
}
