import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Cadastro } from './cadastro.model';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  baseUrl = `${environment.api}/cadastro`;

  constructor(private httpClient: HttpClient) { }

  get() : Observable<Cadastro[]> {
    return this.httpClient.get<Cadastro[]>(this.baseUrl);
  }

  save(cadastro: Cadastro): Observable<Cadastro> {
    if(cadastro.id) {
      return this.update(cadastro);
    }
    return this.httpClient.post<Cadastro>(this.baseUrl, cadastro);
  }

  update(cadastro: Cadastro) : Observable<Cadastro> {
    return this.httpClient.put<Cadastro>(`${this.baseUrl}/${cadastro.id}`, cadastro);
  }

  findById(id: string) : Observable<Cadastro> {
    return this.httpClient.get<Cadastro>(`${this.baseUrl}/${id}`);
  }

  delete(ids: number[]) : Promise<void[]> {
    let promises = ids.map((id) => this.deleteRecursive(id));
    return Promise.all(promises);
  }

  private deleteRecursive(id: number) : Promise<void>{
    return new Promise((resolve) => {
      this.httpClient.delete(`${this.baseUrl}/${id}`).subscribe(() => resolve());
    })
  }

}
