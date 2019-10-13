import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { ExchangeCurrency } from '../../models/exchange-currency';
import { ExchangeCurrencyService } from '../../services/exchange-currency.service';
import { BankAccountService } from '../../services/bank-account.service';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-exchange-page',
  templateUrl: './exchange-page.component.html',
  styleUrls: ['./exchange-page.component.scss']
})
export class ExchangePageComponent implements OnInit {

  bankAccounts: BankAccount[];
  exchangeCurrency: ExchangeCurrency;
  currencyList: string[];
  faArrowRight = faArrowRight;

  constructor(private exchangeCurrencyService: ExchangeCurrencyService,
    private bankAccountService: BankAccountService) { }

  ngOnInit() {
    this.fetchBankAccounts();
  }

  fetchBankAccounts() {
    this.bankAccountService.findByUser()
      .subscribe(res => {
        this.bankAccounts = res.filter(e => e.bankAccType.bankAccountType === 'MULTI_CURRENCY');
      });
    this.exchangeCurrency = new ExchangeCurrency();
  }

  changeCurrencyList() {
    this.currencyList = this.bankAccounts
      .find(e => e.number === this.exchangeCurrency.sourceBankAccNumber)
      .saldos
      .map(e => String(e.currencyType.name));
    }
}
