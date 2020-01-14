import { Component, OnInit } from '@angular/core';
import { ExchangeCurrency } from '../../../models/exchange-currency';
import { ExchangeCurrencyService } from '../../../services/exchange-currency.service';
import { BankAccount } from '../../../models/bank-account';
import { BankAccountService } from '../../../services/bank-account.service';
@Component({
  selector: 'app-exchange-currency',
  templateUrl: './exchange-currency.component.html',
  styleUrls: ['./exchange-currency.component.scss']
})
export class ExchangeCurrencyComponent implements OnInit {

  bankAccounts: BankAccount[];
  exchangeCurrency: ExchangeCurrency;
  currencyList: string[];

  constructor(private exchangeCurrencyService: ExchangeCurrencyService,
    private bankAccountService: BankAccountService) {
    this.bankAccountService.findAll()
    .subscribe(res => this.bankAccounts = res);
    this.exchangeCurrency = new ExchangeCurrency();
  }

  ngOnInit() {
  }

  save() {
    this.exchangeCurrencyService.create(this.exchangeCurrency)
      .subscribe(res => console.log(res));
  }

  changeCurrencyList() {
    this.currencyList = this.bankAccounts
      .find(e => e.number === this.exchangeCurrency.sourceBankAccNumber)
      .saldos
      .map(e => String(e.currencyType.name))
  }

}
