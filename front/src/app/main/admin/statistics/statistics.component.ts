import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountTypeService } from '../../services/bank-account-type.service';
import { BankAccType } from '../../models/bank-acc-type';
import { BankAccountService } from '../../services/bank-account.service';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {

  bankAccountTypes: BankAccType[];
  bankAccountTypesColumns = ['bankAccountType', 'transactionComission', 'exchangeCurrencyCommission', 'edit'];
  public chartType: string = 'doughnut';
  public chartDatasets: Array<any> = [
    { data: [], label: 'My First dataset' }
  ];
  public chartLabels: Array<any> = [];
  public chartColors: Array<any> = [
    {
      backgroundColor: ['#F7464A', '#46BFBD', '#FDB45C'],
      hoverBackgroundColor: ['#FF5A5E', '#5AD3D1', '#FFC870'],
      borderWidth: 2,
    }
  ];

  constructor(private bankAccountTypeService: BankAccountTypeService,
    private bankAccountService: BankAccountService) { }

  ngOnInit() {
    this.fetchBankAccountTypes();
  }

  fetchBankAccountTypes() {
    this.bankAccountTypeService.findAll()
      .subscribe(res => {
        this.bankAccountTypes = res;
        this.bankAccountTypes.sort((o1, o2) => o1.bankAccountType.localeCompare(o2.bankAccountType));
        this.fillChartData();
      });
  }

  fillChartData() {
    let typesCounts: number[] = [];
    let typesNames: string[] = [];
    this.bankAccountTypes.forEach(e => {
      this.bankAccountService.getCountByBankAccountTypeId(e.id).subscribe(res => {
        this.chartDatasets[0].data.push(res);
        this.chartLabels.push(this.formatBankAccountType(e.bankAccountType));
      });
    });
    // this.chartDatasets.push([{ data: typesCounts, label: 'Wykres' }]);
  }

  public chartClicked(e: any): void { }
  public chartHovered(e: any): void { }

  formatBankAccountType(type: string) {
    switch (type) {
      case 'MULTI_CURRENCY':
        return 'Wielowalutowe';
      case 'STUDENT':
        return 'Studenckie';
      default:
        return 'Standardowe';
    }
  }
}
