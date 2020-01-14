import { Component, OnInit } from '@angular/core';
import { BankAccountService } from '../../../services/bank-account.service';
import { BankAccount } from '../../../models/bank-account';
import { TransactionService } from '../../../services/transaction.service';
import { Transaction } from '../../../models/transaction';
import { BankAccType } from '../../../models/bank-acc-type';
import { BankAccountTypeService } from '../../../services/bank-account-type.service';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material';

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
  bankAccountForm: FormGroup;
  selectedIndex: number = -1;

  constructor(private bankAccountService: BankAccountService,
    private transactionService: TransactionService,
    private snackBar: MatSnackBar,
    private bankAccountTypeService: BankAccountTypeService,
    private fb: FormBuilder) {

    this.transaction = new Transaction();
    this.fetchBankAccounts();
    this.fetchBankAccountTypes();
    this.bankAccountForm = fb.group({ bankAccountType: ['', Validators.required] });
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
    this.bankAccountService.findByUser()
      .subscribe(res => this.bankAccounts = res);
  }

  fetchBankAccountTypes() {
    this.bankAccountTypeService.findAll()
      .subscribe(res => this.bankAccountTypes = res);
  }

  changeBankAccountFormType(index: number) {

    if (index >= 0 && index < this.bankAccountTypes.length) {
      this.selectedIndex = index;
      this.bankAccountForm.get('bankAccountType').setValue(this.bankAccountTypes[index].bankAccountType);
    }
  }

  createBankAccount() {
    if (this.selectedIndex < 0 || this.selectedIndex >= this.bankAccountTypes.length) {
      alert('Niepoprawna wartosc');
      return;
    }

    if(this.bankAccounts.length >= 5){
      this.fetchBankAccounts(); this.snackBar.open('Osiągnąłeś limit rachunków', '', { duration: 3000, panelClass: 'red-snackbar' });
      return;
    }
    this.bankAccountService.create(this.bankAccountForm.value)
      .subscribe(res => {
        this.fetchBankAccounts(); this.snackBar.open('Utworzono nowy rachunek', '', { duration: 3000, panelClass: 'green-snackbar' });
      });
  }

  copyToClipboard(item) {
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', (item));
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
    this.snackBar.open('Skopiowano', '', { duration: 3000, panelClass: 'green-snackbar' });
  }
}
