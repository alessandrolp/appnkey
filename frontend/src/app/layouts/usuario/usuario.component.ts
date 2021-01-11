import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { Usuario } from './usuario.model';
import { UsuarioService } from './usuario.service';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  usuario: Usuario = {
    nome: '',
    email: '',
    senha: '',
    confirmacaoSenha: '',
  }

  constructor(
    private router: Router,
    private usuarioService: UsuarioService,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
  }

  salvar() {
    this.usuarioService.save(this.usuario).subscribe(() => {
      let login = {
        "email" : this.usuario.email,
        "senha" : this.usuario.senha
      }
      this.loginService.login(login);
    })
  }
  

  voltar() {
    this.router.navigate(['/login']);
  }

}
