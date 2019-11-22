import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountService } from '../../services/bank-account.service';
import { Observable } from 'rxjs';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { PaymentService } from '../../services/payment.service';
import { MatSnackBar, MatTableDataSource, MatDialog } from '@angular/material';
import { BankAccountDialogComponent } from '../misc/bank-account-dialog/bank-account-dialog.component';
import { forkJoin } from 'rxjs';

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
  }

  createPaymentForm() {
    this.paymentForm = this.fb.group({
      destinedBankAccountNumber: [''],
      sourceCurrencyType: ['', Validators.required],
      balance: ['', Validators.required]
    })
  }

  fetchBankAccounts() {
    this.bankAccountService.findAll()
      .subscribe(res => {
        this.bankAccounts = res;
        this.bankAccountList.data = res;
      });
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

  sendPaymentForm() {
    if (this.paymentForm.invalid) {
      return;
    }
    this.paymentService.create(this.paymentForm.value).subscribe(res => {
      this.snackBar.open('Utworzono wpłatę', '', { duration: 3000, panelClass: 'green-snackbar' });

      this.createPaymentForm();
    }, err =>
      this.snackBar.open(err.error.message, '', { duration: 3000, panelClass: 'red-snackbar' }));
  }

  openBankAccountDetails(id: any){
      const dialogRef = this.dialog.open(BankAccountDialogComponent, {
        width: window.innerWidth > 768 ? '50%' : '85%',
        data: { id: id }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          let bankAccount: BankAccount = result;
          let observables: Observable<any>[] = [];

          bankAccount.saldos.forEach(item => {
            observables.push(this.bankAccountService.updateSaldo(item.id, item));
          });
    
          observables.push(this.bankAccountService.update(id, bankAccount));
          forkJoin(observables).subscribe(array => this.fetchBankAccounts());
          this.snackBar.open('Zaktualizowano konto bankowe', '', { duration: 3000, panelClass: 'green-snackbar' });
        }
      });
    
  }

}
