import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountService } from '../../services/bank-account.service';
import { Observable } from 'rxjs';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';

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

  constructor(private bankAccountService: BankAccountService,
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
      .subscribe(res => this.bankAccounts = res);
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
    }else{
      this.currencyList = [];
    }
  }
}
