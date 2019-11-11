import { Injectable } from '@angular/core';
import { Investment } from '../models/investment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InvestmentService {

  url = 'http://localhost:8080/api/investments';

  constructor(private http: HttpClient) { }

  findAllByUser() {
    return this.http.get<Investment[]>(this.url + '/byUser');
  }
}
