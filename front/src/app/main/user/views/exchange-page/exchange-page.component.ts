import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../../models/bank-account';
import { ExchangeCurrency } from '../../../models/exchange-currency';
import { ExchangeCurrencyService } from '../../../services/exchange-currency.service';
import { BankAccountService } from '../../../services/bank-account.service';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { MatSnackBar } from '@angular/material';
import { CurrencyType } from '../../../models/currency-type';
import { CurrencyTypeService } from '../../../services/currency-type.service';

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
  convertedValue: string;
  beforeConvertValue: any;
  beforeConvertCurrType: any;
  destConvertCurrType: any;
  sourceConvertCurrType: any;
  currencyTypes: CurrencyType[];
  currencyColumns: string[] = ['name', 'exchangeRate'];

  constructor(private exchangeCurrencyService: ExchangeCurrencyService,
    private currencyTypesService: CurrencyTypeService,
    private snackBar: MatSnackBar,
    private bankAccountService: BankAccountService) { }

  ngOnInit() {
    this.fetchBankAccounts();
    this.fetchCurrencyTypes();
  }

  fetchCurrencyTypes() {
    this.currencyTypesService.findAll()
      .subscribe(res => this.currencyTypes = res);
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

  calculate() {
    if (!this.exchangeCurrency.balance || this.exchangeCurrency.balance <= 0) {
      this.snackBar.open('Kwota musi być większa od 0', '', { duration: 3000, panelClass: 'red-snackbar' });
      return;
    }

    this.beforeConvertValue = this.exchangeCurrency.balance;
    this.beforeConvertCurrType = this.exchangeCurrency.sourceCurrency;
    this.destConvertCurrType = this.exchangeCurrency.destCurrency;

    this.exchangeCurrencyService.calculate(this.exchangeCurrency)
      .subscribe(res => this.convertedValue = res);
  }

  convert() {
    if (!this.exchangeCurrency.balance || this.exchangeCurrency.balance <= 0) {
      this.snackBar.open('Kwota musi być większa od 0', '', { duration: 3000, panelClass: 'red-snackbar' });
      return;
    }

    this.exchangeCurrencyService.create(this.exchangeCurrency)
      .subscribe(res => this.snackBar.open('Przekonwertowano walutę', '', { duration: 3000, panelClass: 'green-snackbar' }),
        err => this.snackBar.open(err.error.message, '', { duration: 3000, panelClass: 'red-snackbar' }));

  }
}
