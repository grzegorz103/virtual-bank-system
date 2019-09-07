import { Component, OnInit } from '@angular/core';
import { BankAccountService } from '../../services/bank-account.service';
import { BankAccount } from '../../models/bank-account';
import { TransactionService } from '../../services/transaction.service';
import { Transaction } from '../../models/transaction';

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

  constructor(private bankAccountService: BankAccountService,
    private transactionService: TransactionService) {
    this.bankAccountService.findAll()
      .subscribe(res => this.bankAccounts = res);
    this.transaction = new Transaction();
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
      .map(e => String(e.currencyType.currency))
  }
}
