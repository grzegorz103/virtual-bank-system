import { Component, OnInit } from '@angular/core';
import { BankAccountService } from '../../services/bank-account.service';
import { BankAccount } from '../../models/bank-account';
import { TransactionService } from '../../services/transaction.service';
import { Transaction } from '../../models/transaction';
import { BankAccType } from '../../models/bank-acc-type';
import { BankAccountTypeService } from '../../services/bank-account-type.service';

@Component({
  selector: 'app-bank-account-list',
  templateUrl: './bank-account-list.component.html',
  styleUrls: ['./bank-account-list.component.scss']
})
export class BankAccountListComponent implements OnInit {

  bankAccounts: BankAccount[];
  transaction: Transaction;

  // dla comboboxa przy wykonywaniu przelewu
  currencyList: string[];

  bankAccountTypes: BankAccType[];
  bankAccountForm: BankAccount;

  constructor(private bankAccountService: BankAccountService,
    private transactionService: TransactionService,
    private bankAccountTypeService: BankAccountTypeService) {

    this.transaction = new Transaction();
    this.fetchBankAccounts();
    this.fetchBankAccountTypes();
    this.bankAccountForm = new BankAccount();
  }

  ngOnInit() {
  }

  createTransaction() {
    this.transaction.destinedCurrency = 'PLN';
    //this.transactionService.create(this.transaction).subscribe(res => console.log(res));
  }

  changeCurrencyList() {
    this.currencyList = this.bankAccounts
      .find(e => e.number === this.transaction.sourceAccountNumber)
      .saldos
      .map(e => String(e.currencyType.name))
  }

  fetchBankAccounts() {
    this.bankAccountService.findAll()
      .subscribe(res => this.bankAccounts = res);
  }

  fetchBankAccountTypes() {
    this.bankAccountTypeService.findAll()
      .subscribe(res => this.bankAccountTypes = res);
  }

  createBankAccount() {
    this.bankAccountService.create(this.bankAccountForm)
      .subscribe(res => this.fetchBankAccounts());
  }
}
