import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../../models/bank-account';
import { BankAccountService } from '../../../services/bank-account.service';
import { TransactionService } from '../../../services/transaction.service';
import { Transaction } from '../../../models/transaction';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TransactionTemplate } from '../../../models/transaction-template';
import { ActivatedRoute } from '@angular/router';
import { TransactionTemplateService } from '../../../services/transaction-template-service.service';
import { MatSnackBar } from '@angular/material';
import { BankAccountNumberValidator } from '../../../misc/bank-account-number-validator';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {

  bankAccounts: BankAccount[];
  transaction: Transaction;

  // dla comboboxa przy wykonywaniu przelewu
  currencyList: string[];

  transactionForm: FormGroup;

  // jesli uzytkownik tworzy przelew zdefiniowany
  definedTransfer: TransactionTemplate;

  errors = false;
  errorText: string;

  constructor(private bankAccountService: BankAccountService,
    private transactionService: TransactionService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private transactionTemplateService: TransactionTemplateService,
    private fb: FormBuilder) {

    this.transactionForm = this.fb.group({
      sourceAccountNumber: [this.definedTransfer ? this.definedTransfer.sourceAccountNumber : '', [Validators.required]],
      sourceCurrency: [this.definedTransfer ? this.definedTransfer.sourceCurrency : 'PLN', Validators.required],
      destinedAccountNumber: [this.definedTransfer ? this.definedTransfer.destinedAccountNumber : '', [Validators.required, BankAccountNumberValidator.validate]],
      destinedCurrency: [this.definedTransfer ? this.definedTransfer.destinedCurrency : 'PLN', Validators.required],
      balance: [this.definedTransfer ? this.definedTransfer.balance : '', [Validators.required, Validators.min(1), Validators.max(1000000)]],
      title: [this.definedTransfer ? this.definedTransfer.title : '', [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    });

    if (this.route.snapshot.queryParams['defined']) {
      this.transactionTemplateService.findOneById(this.route.snapshot.queryParams['defined'])
        .subscribe(res => this.fillFormWithTemplate(res));
    }

    this.bankAccountService.findByUser()
      .subscribe(res => this.bankAccounts = res);
    this.transaction = new Transaction();

  }

  // przenioslem z konstruktora
  ngOnInit() {

  }

  fillFormWithTemplate(definedTransfer: TransactionTemplate) {
    if (definedTransfer) {
      this.transactionForm.get('sourceAccountNumber').setValue(definedTransfer.sourceAccountNumber);
      this.transactionForm.get('destinedAccountNumber').setValue(definedTransfer.destinedAccountNumber);
      this.transactionForm.get('balance').setValue(definedTransfer.balance);
      this.transactionForm.get('title').setValue(definedTransfer.title);
    }
  }

  bankAccountNumberValidator() {

  }

  createTransaction() {
    this.transactionService.create(this.transactionForm.value).subscribe(res => {
      this.snackBar.open('Transakcja zakończona', '', { duration: 3000, panelClass: 'green-snackbar' });
    }, err => {
      this.errorText = err.error.message;
      this.errors = true;
      this.snackBar.open('Transakcja zakończona niepowodzeniem', '', { duration: 3000, panelClass: 'red-snackbar' });
    });
    //  this.transaction.destinedCurrency = 'PLN';
    //this.transactionService.create(this.transaction).subscribe(res => console.log(res));
  }

  changeCurrencyList() {
    this.currencyList = this.bankAccounts
      .find(e => e.number === this.transaction.sourceAccountNumber)
      .saldos
      .map(e => String(e.currencyType.name))
  }

  getAvailableFunds(bankAccount: BankAccount) {
    return bankAccount.saldos
      .find(e => e.currencyType.name === 'PLN')
      .balance;
  }
}
