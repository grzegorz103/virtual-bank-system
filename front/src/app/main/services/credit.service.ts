import { Injectable } from '@angular/core';
import { Credit } from '../models/credit';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CreditService {

  url = 'http://localhost:8080/api/credits';

  constructor(private http: HttpClient) { }

  create(credit: any){
    return this.http.post<Credit>(this.url, credit);
  }
}
