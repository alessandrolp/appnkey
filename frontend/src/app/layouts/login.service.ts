import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import jwtDecode, * as jwt_decode from 'jwt-decode';
import { environment } from 'src/environments/environment';
import { Login } from './login/login.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  baseUrl = `${environment.api}/login`;

  constructor(
    private http: HttpClient,
    private router : Router  
  ) { }
  
  login(login: Login) {
    this.http.post(this.baseUrl, login, {observe : 'response'}).subscribe(response => {
      const token = response.headers.get('Authorization');
      if (response && token) {
        window.localStorage.setItem('token', token);
        this.router.navigate(['/']);
      }
    })
  }

  logout() {
    window.localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

  getToken() {
    return window.localStorage.getItem('token');
  }

  getTokenExpirationDate(token: string): Date {
    const decoded: any = jwtDecode(token);

    if (decoded.exp === undefined) {
      return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if (!token) {
      return true;
    }

    const date = this.getTokenExpirationDate(token);
    if (date === undefined) {
      return false;
    }

    return !(date.valueOf() > new Date().valueOf());
  }

  isUserLoggedIn() {
    const token = this.getToken();

    if (!token) {
      return false;
      
    } else if (this.isTokenExpired(token)) {
      return false;
    }

    return true;
  }

}
