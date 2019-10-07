import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  create(userForm: string) {
    return this.http.post(this.url + '/users', userForm);
  }

  login(username: string, password: string) {
    return this.http.get(this.url + '/authenticate?username=' + username + '&password=' + password, { observe: 'response' });
  }
}
