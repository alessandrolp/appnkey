import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { Login } from './login.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  login: Login = {
    email : "",
    senha : ""
  }

  constructor(
    private router: Router,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
  }

  entrar() {
    this.loginService.login(this.login);
  }

  cadastrar() {
    this.router.navigate(['/usuario']);
  }

}
