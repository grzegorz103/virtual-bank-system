import { Component, OnInit } from '@angular/core';
import { TransactionTemplate } from 'src/app/main/models/transaction-template';
import { TransactionTemplateService } from 'src/app/main/services/transaction-template-service.service';
import { MatDialog, MatTableDataSource, MatSnackBar } from '@angular/material';
import { DialogWindowComponent } from 'src/app/main/misc/dialog-window/dialog-window.component';
import { SelectionModel } from '@angular/cdk/collections';
import { forkJoin } from 'rxjs';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-template-list',
  templateUrl: './template-list.component.html',
  styleUrls: ['./template-list.component.scss']
})
export class TemplateListComponent implements OnInit {

  templates: TransactionTemplate[];
  selection = new SelectionModel<TransactionTemplate>(true, []);
  headers = [/* 'ID', */ 'name', 'sourceAccountNumber', 'destinedAccountNumber', 'sourceCurrency', 'balance', 'create', 'edit'];
  isRemoving = false;

  // do dodawania/edycji
  template: TransactionTemplate;
  constructor(private transactionTemplateService: TransactionTemplateService,
    private snackBar: MatSnackBar,
    public dialog: MatDialog) { }

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.transactionTemplateService.findAllByCurrentUser().subscribe(res => {
      this.templates = res;
      this.templates.sort((o1, o2) => o1.id - o2.id);
    });
  }

  openDialog(index?: any) {
    let isCreating: boolean;
    if (index) {
      isCreating = false;

    } else {
      isCreating = true;
      this.template = new TransactionTemplate();
    }
    const dialogRef = this.dialog.open(DialogWindowComponent, {
      width: window.innerWidth > 768 ? '50%' : '95%',
      data: { templateId: index }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (isCreating) {
          this.transactionTemplateService.create(result).subscribe(res => {
            this.fetchData();
            this.snackBar.open('Utworzono', '', { duration: 3000, panelClass: 'green-snackbar' });
          }, err => {
            this.snackBar.open(err.error.messages, '', { duration: 5000, panelClass: 'red-snackbar' });
          });
        } else {
          this.transactionTemplateService.update(index, result).subscribe(res => {
            this.fetchData(); this.snackBar.open('Przelew został zaktualizowany', '', { duration: 3000, panelClass: 'green-snackbar' });
          }, err =>
            this.snackBar.open(err.error.messages, '', { duration: 5000, panelClass: 'red-snackbar' })
          );
        }
      }
    });
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.templates.length;
    return numSelected === numRows;
  }

  removeSelectedRows() {

    let observables: Observable<any>[] = [];

    this.selection.selected.forEach(item => {
      let index: number = this.templates.findIndex(e => e === item);
      observables.push(this.transactionTemplateService.removeById(item.id));
    });

    forkJoin(observables).subscribe(array => this.fetchData());
    this.selection = new SelectionModel<TransactionTemplate>(true, []);
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.templates.forEach(row => this.selection.select(row));
  }

  switchDeleteStatus() {
    this.isRemoving = !this.isRemoving;

    if (this.isRemoving) {
      this.headers = ['name', 'sourceAccountNumber', 'destinedAccountNumber', 'sourceCurrency', 'balance', 'create', 'edit', 'select'];
    } else {
      this.headers = ['name', 'sourceAccountNumber', 'destinedAccountNumber', 'sourceCurrency', 'balance', 'create', 'edit'];
    }
  }

}
