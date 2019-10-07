import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CoreGuardService implements CanActivate{

  constructor(private router: Router, private authService:AuthService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = this.authService.getToken();
    if (token && !this.authService.isTokenExpired()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      this.authService.clearToken();
      return false;
    }
  }

}
