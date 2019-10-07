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

  getDecodedToken(): string {
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
}
