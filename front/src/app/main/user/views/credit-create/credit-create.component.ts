import {Component, OnInit} from '@angular/core';
import {Options} from 'ng5-slider';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {BankAccountService} from '../../../services/bank-account.service';
import {BankAccount} from '../../../models/bank-account';
import {CreditService} from '../../../services/credit.service';
import Big from 'big.js';
import {MatSnackBar} from '@angular/material';
import {faQuestionCircle} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-credit-create',
  templateUrl: './credit-create.component.html',
  styleUrls: ['./credit-create.component.scss']
})
export class CreditCreateComponent implements OnInit {

  balanceSliderValue = 1000;
  balanceSlider: Options = {
    floor: 1000,
    ceil: 10000,
    step: 100
  }

  monthSliderValue = 12;
  monthSlider: Options = {
    floor: 6,
    ceil: 24,
    step: 1,
  };

  installmentValue = 0;

  bankAccounts: BankAccount[];
  form: FormGroup;
  selectedBankAccount: BankAccount;
  currencyType: string;
  faQuestionCircle = faQuestionCircle;

  constructor(private fb: FormBuilder,
              private snackBar: MatSnackBar,
              private bankAccountService: BankAccountService,
              private creditService: CreditService) {
    bankAccountService.findByUser().subscribe(res => {
      this.bankAccounts = res;
      this.form = this.fb.group({
        destinedSaldoId: ['', Validators.required],
        totalBalance: '',
        totalInstallmentCount: '',
      });
    });
  }

  ngOnInit() {
  }

  createCredit() {
    this.creditService.create(this.form.value).subscribe(res =>
      this.snackBar.open('Utworzono wniosek', '', {duration: 3000, panelClass: 'green-snackbar'})
    );
  }

  calculateInstallment(event: any) {
    this.installmentValue = Big(this.balanceSliderValue / this.monthSliderValue).round(2, 1).valueOf();
  }

  switchCurrencyType(event: any) {
    this.currencyType = event.source.selected.viewValue;
  }
}
