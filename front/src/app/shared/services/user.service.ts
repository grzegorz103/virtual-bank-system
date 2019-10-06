import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  create(userForm: string) {
    return this.http.post(this.url, userForm);
  }
}
