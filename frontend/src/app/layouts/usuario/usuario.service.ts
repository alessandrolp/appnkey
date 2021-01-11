import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Usuario } from './usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  baseUrl = `${environment.api}/usuario`;

  constructor(private httpClient: HttpClient) { }

  save(usuario: Usuario): Observable<void> {
    return this.httpClient.post<void>(this.baseUrl, usuario);
  }
  
}
