import { NgModule, LOCALE_ID } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CadastroComponent } from './components/cadastro.component';
import { CadastroFormComponent } from './components/form/cadastro-form.component';
import { MatButtonModule } from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatSortModule } from '@angular/material/sort';
import { MatCheckboxModule } from '@angular/material/checkbox';

import localePt from '@angular/common/locales/pt';
import {registerLocaleData} from '@angular/common';
import { AppDialogComponent } from 'src/app/shared/app-dialog/app-dialog.component';
registerLocaleData(localePt);

@NgModule({
  declarations: [
    CadastroComponent,
    CadastroFormComponent
  ],
  entryComponents: [
    AppDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatTableModule,
    MatInputModule,
    MatSortModule,
    MatCheckboxModule,
  ],
  providers: [{
    provide: LOCALE_ID,
    useValue: 'pt-BR',
  }]
})
export class CadastroModule { }
