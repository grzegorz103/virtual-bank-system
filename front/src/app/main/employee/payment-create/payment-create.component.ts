import { Component, OnInit, ViewChild } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountService } from '../../services/bank-account.service';
import { Observable } from 'rxjs';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { PaymentService } from '../../services/payment.service';
import { MatSnackBar, MatTableDataSource, MatDialog, MatPaginator } from '@angular/material';
import { BankAccountDialogComponent } from '../misc/bank-account-dialog/bank-account-dialog.component';
import { forkJoin } from 'rxjs';
import { Payment } from '../../models/payment';

@Component({
  selector: 'app-payment-create',
  templateUrl: './payment-create.component.html',
  styleUrls: ['./payment-create.component.scss']
})
export class PaymentCreateComponent implements OnInit {

  bankAccounts: BankAccount[];
  filteredBankAccounts: Observable<BankAccount[]>;
  paymentForm: FormGroup;
  currencyList: string[];
  bankAccountList = new MatTableDataSource<BankAccount>();
  payments = new MatTableDataSource<Payment>();

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  @ViewChild(MatPaginator, { static: true })
  paginatorPayments: MatPaginator;

  constructor(private bankAccountService: BankAccountService,
    private paymentService: PaymentService,
    private snackBar: MatSnackBar,
    public dialog: MatDialog,
    private fb: FormBuilder) {
    this.createPaymentForm();
  }

  ngOnInit() {
    this.filteredBankAccounts = this.paymentForm.get('destinedBankAccountNumber').valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
    this.fetchBankAccounts();
    this.fetchPayments();
  }

  createPaymentForm() {
    this.paymentForm = this.fb.group({
      destinedBankAccountNumber: ['', Validators.required],
      sourceCurrencyType: ['', Validators.required],
      balance: ['', [Validators.required, Validators.min(1), Validators.max(1000000)]]
    })
  }

  fetchBankAccounts() {
    this.bankAccountService.findAll()
      .subscribe(res => {
        this.bankAccounts = res;
        this.bankAccountList.data = res;
        this.bankAccountList.paginator = this.paginator;
      });
  }

  fetchPayments() {
    this.paymentService.findAll()
      .subscribe(res => {
        res.sort((o1, o2) => new Date(o2.date).getTime() - new Date(o1.date).getTime());
        this.payments.data = res;
        this.payments.paginator = this.paginatorPayments;
      })
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

  private _filter(value: string) {
    this.changeCurrencyList();
    const filterValue = value.toLowerCase();

    return this.bankAccounts
      .filter(option => option.number.toLowerCase().includes(filterValue));
  }

  changeCurrencyList() {
    const selectedAccount = this.bankAccounts
      .find(e => e.number === this.paymentForm.get('destinedBankAccountNumber').value);

    if (selectedAccount) {
      this.currencyList = selectedAccount.saldos
        .map(e => e.currencyType.name);
    } else {
      this.currencyList = [];
    }
  }

  applyFilter(filterValue: string) {
    this.bankAccountList.filter = filterValue.trim().toLowerCase();
  }

  applyFilterPayments(filterValue: string) {
    this.payments.filter = filterValue.trim().toLowerCase();
  }

  sendPaymentForm() {
    if (this.paymentForm.invalid) {
      return;
    }
    this.paymentService.create(this.paymentForm.value).subscribe(res => {
      this.snackBar.open('Utworzono wpłatę', '', { duration: 3000, panelClass: 'green-snackbar' });
      this.fetchPayments();
      this.createPaymentForm();
      this.fetchBankAccounts();
    }, err =>
      this.snackBar.open(err.error.message, '', { duration: 3000, panelClass: 'red-snackbar' }));
  }

  openBankAccountDetails(id: any) {
    const dialogRef = this.dialog.open(BankAccountDialogComponent, {
      width: window.innerWidth > 768 ? '60%' : '95%',
      height: '80%',
      data: { id: id }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchBankAccounts();
      if (result) {
        let bankAccount: BankAccount = result;
        let observables: Observable<any>[] = [];

        bankAccount.saldos.forEach(item => {
          observables.push(this.bankAccountService.updateSaldo(item.id, item));
        });

        observables.push(this.bankAccountService.update(id, bankAccount));
        forkJoin(observables).subscribe(array => {
          this.fetchBankAccounts();
          this.snackBar.open('Zaktualizowano konto bankowe', '', { duration: 3000, panelClass: 'green-snackbar' });
        }, err => {
          if (err.error.messages) {
            this.snackBar.open(err.error.messages, '', { duration: 3000, panelClass: 'red-snackbar' });
          } else {
            this.snackBar.open(err.error.message, '', { duration: 3000, panelClass: 'red-snackbar' });
          }
        });
      }
    });
  }

}
