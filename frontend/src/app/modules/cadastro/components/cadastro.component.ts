import { SelectionModel } from '@angular/cdk/collections';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { AppDialogComponent } from 'src/app/shared/app-dialog/app-dialog.component';
import { Cadastro } from '../cadastro.model';
import { CadastroService } from '../cadastro.service';
import { CadastroDataSource } from './cadastro-datasource';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<Cadastro>;

  dataSource: CadastroDataSource;

  displayedColumns = ['select', 'nome', 'data', 'usuario'];

  selection = new SelectionModel<Cadastro>(true, []);

  cadastros: Cadastro[];
  
  btnDeleteDisabled = true;
  btnEditDisabled = true;

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private cadastroService: CadastroService,
  ) { }

  ngOnInit() {
    this.dataSource = new CadastroDataSource(this.cadastroService);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  onAddCadastro(): void {
    this.router.navigate(['/cadastro/form']);
  }

  onEditCadastro(): void {
    const id = this.getCadastrosSelecionados()[0].id;
    this.router.navigate(['cadastro/form/' + id]);
  }

  onDeleteCadastro(): void {
    let dialog = this.dialog.open(AppDialogComponent);
    dialog.afterClosed().subscribe(confirm => {
      if(confirm == 'true') {
        let ids = this.getCadastrosSelecionados().map(s => s.id);
        this.cadastroService.delete(ids).then(() => {
          this.cadastroService.get().subscribe(cadastros => {
            this.table.dataSource = cadastros;
          })
        })
      }
    });
  }

  selectedItem() {
    let cadastrosSelecionados = this.getCadastrosSelecionados();
    this.btnDeleteDisabled = cadastrosSelecionados.length == 0;
    this.btnEditDisabled = cadastrosSelecionados.length != 1;
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
        this.selection.clear() :
        this.dataSource.data.forEach(row => this.selection.select(row));

    this.selectedItem();    
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: Cadastro): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }

  private getCadastrosSelecionados() : Cadastro[] {
    return this.selection.selected;
  }
 

}
