import { Component, OnInit } from '@angular/core';
import { BankAccountService } from '../bank-account.service';
import { BankAccount } from '../bank-account';

@Component({
  selector: 'app-bank-account-list',
  templateUrl: './bank-account-list.component.html',
  styleUrls: ['./bank-account-list.component.scss']
})
export class BankAccountListComponent implements OnInit {

  bankAccounts: BankAccount[];

  constructor(private bankAccountService: BankAccountService) {
    this.bankAccountService.findAll()
      .subscribe(res => this.bankAccounts = res);
  }

  ngOnInit() {
  }

}
