import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  helper = new JwtHelperService();

  constructor() { }

  saveToken(token: string) {
    if (token) {
      localStorage.setItem('token', token);
    }
  }

  getDecodedToken() {
    return this.helper.decodeToken(this.getToken());
  }

  isTokenExpired(): boolean {
    return this.helper.isTokenExpired(this.getToken());
  }

  clearToken(): void {
    localStorage.removeItem('token');
  }

  getToken(): string {
    return localStorage.getItem('token');
  }

  setUserRoles() {
    localStorage.setItem('userRoles', JSON.stringify(this.getDecodedToken().rol));
  }

  getUserRoles() {
    return JSON.parse(localStorage.getItem('userRoles'));
  }

  hasAdminRole() {
    return this.getUserRoles().some(role => role === 'ROLE_ADMIN');
  }

  hasUserRole() {
    return this.getUserRoles().some(role => role === 'ROLE_USER');
  }

  hasEmployeeRole() {
    return this.getUserRoles().some(role => role === 'ROLE_EMPLOYEE');
  }

  clearLocalStorage() {
    localStorage.clear();
  }

  clearSessionStorage() {
    sessionStorage.clear();
  }

  setUserIdentifier() {
    localStorage.setItem('identifier', JSON.parse(this.getDecodedToken().id));
  }

  getUserIdentifier() {
    return localStorage.getItem('identifier');
  }
}
