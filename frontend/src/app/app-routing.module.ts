import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { DefaultComponent } from './layouts/default/default.component';
import { LoginComponent } from './layouts/login/login.component';
import { UsuarioComponent } from './layouts/usuario/usuario.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { CadastroFormComponent } from './modules/cadastro/components/form/cadastro-form.component';
import { CadastroComponent } from './modules/cadastro/components/cadastro.component';

const routes: Routes = [{
    path: '',
    component: DefaultComponent,
      children : [{
        path: '',
        component: DashboardComponent
      },{
        path: 'cadastro',
        component: CadastroComponent,
      },{
        path: 'cadastro/form',
        component: CadastroFormComponent
      },{
        path: 'cadastro/form/:id',
        component: CadastroFormComponent
      }],
      canActivate: [AuthGuard]
  }, {
    path: 'login',
    component: LoginComponent,
  }, {
    path: 'usuario',
    component: UsuarioComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
