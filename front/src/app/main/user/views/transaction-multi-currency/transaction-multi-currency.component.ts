import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../../models/bank-account';
import { BankAccountService } from '../../../services/bank-account.service';
import { TransactionService } from '../../../services/transaction.service';
import { Transaction } from '../../../models/transaction';
import { TransactionTemplate } from '../../../models/transaction-template';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TransactionTemplateService } from '../../../services/transaction-template-service.service';
import { CurrencyTypeService } from '../../../services/currency-type.service';
import { CurrencyType } from '../../../models/currency-type';
import { MatSnackBar } from '@angular/material';
import { BankAccountNumberValidator } from '../../../misc/bank-account-number-validator';

@Component({
  selector: 'app-transaction-multi-currency',
  templateUrl: './transaction-multi-currency.component.html',
  styleUrls: ['./transaction-multi-currency.component.scss']
})
export class TransactionMultiCurrencyComponent implements OnInit {


  bankAccounts: BankAccount[];
  transaction: Transaction;

  // dla comboboxa przy wykonywaniu przelewu
  currencyList: string[];

  // dla docelowej waluty
  currencyTypes: CurrencyType[];

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
    private currencyTypeService: CurrencyTypeService,
    private fb: FormBuilder) {

    this.transactionForm = this.fb.group({
      sourceAccountNumber: ['', Validators.required],
      sourceCurrency: ['', Validators.required],
      destinedAccountNumber: ['', [Validators.required, BankAccountNumberValidator.validate]],
      destinedCurrency: ['', Validators.required],
      balance: ['', [Validators.required, Validators.min(1), Validators.max(1000000)]],
      title: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    });

    this.bankAccountService.findByUser()
      .subscribe(res => {
        this.bankAccounts = res;
        this.changeCurrencyList();
        if (this.route.snapshot.queryParams['defined']) {
          this.transactionTemplateService.findOneById(this.route.snapshot.queryParams['defined'])
            .subscribe(res => { this.fillFormWithTemplate(res); this.changeCurrencyList() });
        }
      });
    this.transaction = new Transaction();

    this.currencyTypeService.findAll()
      .subscribe(res => this.currencyTypes = res);

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
      this.transactionForm.get('sourceCurrency').setValue(definedTransfer.sourceCurrency);
      this.transactionForm.get('destinedCurrency').setValue(definedTransfer.destinedCurrency);
    }
  }

  createTransaction() {
    this.transactionService.create(this.transactionForm.value).subscribe(res =>
      this.snackBar.open('Transakcja zakończona', '', { duration: 3000, panelClass: 'green-snackbar' }),
      err => {
        this.errorText = err.error.message;
        this.errors = true;
        this.snackBar.open('Transakcja zakończona niepowodzeniem', '', { duration: 3000, panelClass: 'red-snackbar' });
      });
    //  this.transaction.destinedCurrency = 'PLN';
    //this.transactionService.create(this.transaction).subscribe(res => console.log(res));
  }

  changeCurrencyList() {
    const sourceAccNumberVal = this.transactionForm.get('sourceAccountNumber').value;
    if (sourceAccNumberVal) {
      this.currencyList = this.bankAccounts
        .find(e => e.number === sourceAccNumberVal)
        .saldos
        .map(e => String(e.currencyType.name));
    }
  }

  getAvailableFunds(currency: string) {
    const sourceAccNumberVal = this.transactionForm.get('sourceAccountNumber').value;
    return this.bankAccounts
      .find(e => e.number === sourceAccNumberVal)
      .saldos
      .find(e => e.currencyType.name === currency)
      .balance;
  }
}
