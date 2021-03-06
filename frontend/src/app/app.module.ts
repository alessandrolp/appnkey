import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { HomeModule } from './layouts/home/home.module';
import { LoginModule } from './layouts/login/login.module';
import { UsuarioModule } from './layouts/usuario/usuario.module';

import { httpInterceptorProviders } from './http-interceptors';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HomeModule,
    LoginModule,
    UsuarioModule,
    HttpClientModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
