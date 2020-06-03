import { Component, OnInit } from '@angular/core';
import { InvestmentService } from '../../../services/investment.service';
import { BankAccountService } from '../../../services/bank-account.service';
import { BankAccount } from '../../../models/bank-account';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import {faQuestionCircle} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-investment-create',
  templateUrl: './investment-create.component.html',
  styleUrls: ['./investment-create.component.scss']
})
export class InvestmentCreateComponent implements OnInit {

  bankAccounts: BankAccount[];
  form: FormGroup;
  selectedBankAccount: BankAccount;
  currencyType: string;
  investmentForm: FormGroup;
  faQuestionCircle = faQuestionCircle;

  constructor(private bankAccountService: BankAccountService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private investmentService: InvestmentService) {
  }

  ngOnInit() {
    this.fetchBankAccounts();
    this.createInvestmentForm();
  }

  createInvestmentForm() {
    this.investmentForm = this.fb.group({
      destinedSaldoId: ['', Validators.required],
      startBalance: ['', [Validators.required, Validators.min(1)]]
    });
  }

  fetchBankAccounts() {
    this.bankAccountService.findByUser()
      .subscribe(res => {
        this.bankAccounts = res;
      });
  }

  sendInvestmentForm() {
    if (this.investmentForm.invalid) {
      return;
    }

    const saldoId = this.investmentForm.get('destinedSaldoId').value;
    const balance = this.investmentForm.get('startBalance').value;

    if(balance <= 0){
      return;
    }

    if (saldoId && this.selectedBankAccount.saldos.find(e => e.id === saldoId).balance < balance) {
      this.snackBar.open('Saldo nie posiada wystarczających środków', '', { duration: 3000, panelClass: 'red-snackbar' });
      return;
    }
    this.investmentService.create(this.investmentForm.value)
      .subscribe(res =>{
        this.snackBar.open('Lokata utworzona', '', { duration: 3000, panelClass: 'green-snackbar' })
        this.router.navigateByUrl('/core/investments/list');
      }, err => this.snackBar.open(err.error.message + ' ' + err.error.messages, '', { duration: 3000, panelClass: 'red-snackbar' })
      );
  }

}
