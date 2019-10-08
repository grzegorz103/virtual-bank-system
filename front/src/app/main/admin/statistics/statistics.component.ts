import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountTypeService } from '../../services/bank-account-type.service';
import { BankAccType } from '../../models/bank-acc-type';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {

  bankAccountTypes: BankAccType[];
  bankAccountTypesColumns = ['bankAccountType', 'transactionComission', 'exchangeCurrencyCommission'];
  constructor(private bankAccountTypeService: BankAccountTypeService) { }

  ngOnInit() {
    this.fetchBankAccountTypes();
  }

  fetchBankAccountTypes() {
    this.bankAccountTypeService.findAll()
      .subscribe(res => this.bankAccountTypes = res);
  }

}
