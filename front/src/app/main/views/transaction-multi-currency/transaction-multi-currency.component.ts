import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountService } from '../../services/bank-account.service';
import { TransactionService } from '../../services/transaction.service';
import { Transaction } from '../../models/transaction';
import { TransactionTemplate } from '../../models/transaction-template';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TransactionTemplateService } from '../../services/transaction-template-service.service';
import { CurrencyTypeService } from '../../services/currency-type.service';
import { CurrencyType } from '../../models/currency-type';

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

  constructor(private bankAccountService: BankAccountService,
    private transactionService: TransactionService,
    private route: ActivatedRoute,
    private transactionTemplateService: TransactionTemplateService,
    private currencyTypeService: CurrencyTypeService,
    private fb: FormBuilder) {

    this.transactionForm = this.fb.group({
      sourceAccountNumber: ['', Validators.required],
      sourceCurrency: ['', Validators.required],
      destinedAccountNumber: ['', Validators.required],
      destinedCurrency: ['', Validators.required],
      balance: ['', Validators.required],
      title: ['', Validators.required],
    });

    if (this.route.snapshot.queryParams['defined']) {
      this.transactionTemplateService.findOneById(this.route.snapshot.queryParams['defined'])
        .subscribe(res => this.fillFormWithTemplate(res));
    }

    this.bankAccountService.findAll()
      .subscribe(res => this.bankAccounts = res);
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
    }
  }

  createTransaction() {
    this.transactionService.create(this.transactionForm.value).subscribe(res => console.log(res));
    //  this.transaction.destinedCurrency = 'PLN';
    //this.transactionService.create(this.transaction).subscribe(res => console.log(res));
  }

  changeCurrencyList() {
    this.currencyList = this.bankAccounts
      .find(e => e.number === this.transactionForm.get('sourceAccountNumber').value)
      .saldos
      .map(e => String(e.currencyType.name));
    this.currencyList.forEach(e => console.log(e));
    console.log('f');
  }

}
