import { Component, OnInit, ViewChild } from '@angular/core';
import { Credit } from '../../models/credit';
import { CreditService } from '../../services/credit.service';
import { MatTableDataSource, MatPaginator, MatSnackBar } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-credit-list',
  templateUrl: './credit-list.component.html',
  styleUrls: ['./credit-list.component.scss']
})
export class CreditListComponent implements OnInit {

  credit: Credit;
  credits = new MatTableDataSource<Credit>();
  isLoading = true;
  searchForm: FormGroup;

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  creditsColumns = ['id', 'totalBalance','destinedAccount', 'totalInstallmentCount', 'currency', 'accept', 'reject'];

  constructor(private creditService: CreditService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.fetchCreditList();
    this.createSearchForm();
  }

  createSearchForm() {
    this.searchForm = this.fb.group({
      id: ['']
    });
  }

  fetchCreditList() {
    this.creditService.findAllByCreditType('AWAITING')
      .subscribe(res => {
        this.credits.data = res;
        this.credits.paginator = this.paginator;
        this.isLoading = false;
      });
  }

  acceptCredit(id: number) {
    this.creditService.changeStatus(id, 'ACTIVE')
      .subscribe(res => {
        this.fetchCreditList();
        this.snackBar.open('Zaakceptowano kredyt', '', { duration: 3000, panelClass: 'green-snackbar' });
      });
  }

  rejectCredit(id: number) {
    this.creditService.changeStatus(id, 'CANCELED')
      .subscribe(res => {
        this.fetchCreditList();
        this.snackBar.open('Odrzucono kredyt', '', { duration: 3000, panelClass: 'blue-snackbar' });
      });
  }

  changeStatus() {
    if (!this.credit.id) {
      return;
    }

    this.creditService.changeStatus(this.credit.id)
      .subscribe(res => {
        this.credit = res;
        this.fetchCreditList();
        this.snackBar.open('Zmieniono status', '', { duration: 3000, panelClass: 'green-snackbar' });
      });
  }

  searchCredit() {
    const id = this.searchForm.get('id').value;
    if (!id) {
      return;
    }

    this.creditService.findById(id)
      .subscribe(res => this.credit = res, err => this.snackBar.open('Nie odnaleziono kredytu', '', { duration: 3000, panelClass: 'red-snackbar' }));
  }

  copyToClipboard(item) {
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', (item));
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
    this.snackBar.open('Skopiowano', '', { duration: 3000, panelClass: 'green-snackbar' });
  }
  
}
