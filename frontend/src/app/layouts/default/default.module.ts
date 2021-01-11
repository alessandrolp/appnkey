import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DefaultComponent } from './default.component';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatSidenavModule } from '@angular/material/sidenav';
import {MatSnackBarModule} from '@angular/material/snack-bar';

import { SharedModule } from 'src/app/shared/shared.module';
import { CadastroModule } from 'src/app/modules/cadastro/cadastro.module';

@NgModule({
  declarations: [
    DefaultComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    CadastroModule,
    SharedModule,
    MatSidenavModule,
    FlexLayoutModule,
    MatSnackBarModule
  ]
})
export class DefaultModule { }
