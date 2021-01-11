import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Cadastro } from '../../cadastro.model';
import { CadastroService } from '../../cadastro.service';

@Component({
  selector: 'app-cadastro-form',
  templateUrl: './cadastro-form.component.html',
  styleUrls: ['./cadastro-form.component.scss']
})
export class CadastroFormComponent implements OnInit {

  horizontalPositionAlert: MatSnackBarHorizontalPosition = 'right';
  verticalPositionAlert: MatSnackBarVerticalPosition = 'top';

  cadastro: Cadastro = {
    nome: '',
  }

  constructor(
    private cadastroService: CadastroService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id) {
      this.cadastroService.findById(id).subscribe(cadastro => {
        this.cadastro = cadastro;
      });
    }
  }

  save() : void {
    this.cadastroService.save(this.cadastro).subscribe(cadastro => {
      this.snackBar.open('Cadastro salvo com sucesso!', 'X', {
        duration: 3000,
        horizontalPosition: this.horizontalPositionAlert,
        verticalPosition: this.verticalPositionAlert
      });
      this.router.navigate(['cadastro']);
    });
  }

  cancel() : void {
    this.router.navigate(['cadastro']);
  }


}
