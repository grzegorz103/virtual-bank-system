import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/main/models/user';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = environment.apiUrl + '/api';

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

  findByUserType(userType: string, disabledOnly?: boolean) {
    const queryParams = disabledOnly ? '?disabledOnly=yes' : '';
    return this.http.get<User[]>(this.url + '/users/type/' + userType + queryParams);
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

  findByIdentifier(identifier: string) {
    return this.http.get<User>(this.url + '/users/byIdentifier/' + identifier);
  }

  changeActivateStatus(userId: string) {
    return this.http.patch<User>(this.url + '/users/' + userId + '/activate', null);
  }

  findCurrentUser() {
    return this.http.get<User>(this.url + '/users/auth/current');
  }

  editPassword(value: string) {
    return this.http.patch<User>(this.url + '/users/password/edit', value);
  }

}
