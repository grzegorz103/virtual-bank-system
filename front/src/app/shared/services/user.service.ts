import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/main/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  create(userForm: string) {
    return this.http.post(this.url + '/users', userForm);
  }

  createEmployee(userForm: string) {
    return this.http.post(this.url + '/users/create/employee', userForm);
  }

  login(username: string, password: string) {
    return this.http.get(this.url + '/authenticate?username=' + username + '&password=' + password, { observe: 'response' });
  }

  findByUserType(userType: string) {
    return this.http.get<User[]>(this.url + '/users/type/' + userType);
  }

  findById(id: string) {
    return this.http.get<User>(this.url + '/users/' + id);
  }

  update(id: string, user: User) {
    return this.http.put<User>(this.url + '/users/' + id, user);
  }

  changeStatus(id: string) {
    return this.http.patch<User>(this.url + '/users/' + id + '/status', null);
  }

  findByIdentifier(identifier: string){
    return this.http.get<User>(this.url +'/users/byIdentifier/' + identifier);
  }
}
