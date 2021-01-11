import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { LoginService } from '../layouts/login.service';
import { catchError } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService, private dialog: MatDialog) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    let token = this.loginService.getToken();
    let request: HttpRequest<any> = req;

    if(token) {
      request = req.clone({
        headers: req.headers.set('Authorization', token)
      })
    }

    return next.handle(request)
      .pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    if(error.error instanceof ErrorEvent) {
      console.error('Ocorreu um erro: ', error.error.message);
    } else {
      console.error(
        `Código do erro ${error.status}, ` + `Erro: ${JSON.stringify(error.error)}`);
    }
    return throwError('Ocorreu um erro, tente novamente');
  }
  
}
