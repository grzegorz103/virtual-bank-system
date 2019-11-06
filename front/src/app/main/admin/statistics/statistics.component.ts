import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountTypeService } from '../../services/bank-account-type.service';
import { BankAccType } from '../../models/bank-acc-type';
import { BankAccountService } from '../../services/bank-account.service';
import { CurrencyTypeService } from '../../services/currency-type.service';
import { MatDialog } from '@angular/material';
import { CurrencyTypeEditComponent } from '../misc/currency-type-edit/currency-type-edit.component';
import { CurrencyType } from '../../models/currency-type';

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
  currencyTypes: CurrencyType[];
  currencyTypesColumns: ['name', 'exchangeRate', 'edit'];

  constructor(private bankAccountTypeService: BankAccountTypeService,
    private currencyTypeService: CurrencyTypeService,
    public dialog: MatDialog,
    private bankAccountService: BankAccountService) { }

  ngOnInit() {
    this.fetchBankAccountTypes();
    this.fetchCurrencyTypes();
  }

  fetchBankAccountTypes() {
    this.bankAccountTypeService.findAll()
      .subscribe(res => {
        this.bankAccountTypes = res;
        this.bankAccountTypes.sort((o1, o2) => o1.bankAccountType.localeCompare(o2.bankAccountType));
        this.fillChartData();
      });
  }

  fetchCurrencyTypes() {
    this.currencyTypeService.findAll()
      .subscribe(res => this.currencyTypes = res);
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

  openCurrencyTypeEditDialog(currencyTypeId: any) {
    if (this.currencyTypes.some(e => e.id === currencyTypeId)) {
      const dialogRef = this.dialog.open(CurrencyTypeEditComponent, {
        width: window.innerWidth > 768 ? '50%' : '85%',
        data: { id: currencyTypeId }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.currencyTypeService.update(currencyTypeId, result)
            .subscribe(res => this.fetchCurrencyTypes());
        }
      });
    }
  }
}
