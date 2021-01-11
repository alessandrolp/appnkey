import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { LoginService } from './layouts/login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private loginService: LoginService
  ){}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    // if(this.loginService.isUserLoggedIn()) {
    //   return true;
    // }
    if(this.loginService.getToken()) {
      return true;
    }
    this.router.navigate(['login']);
    return false;
  }
  
}
