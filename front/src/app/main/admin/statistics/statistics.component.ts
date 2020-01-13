import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountTypeService } from '../../services/bank-account-type.service';
import { BankAccType } from '../../models/bank-acc-type';
import { BankAccountService } from '../../services/bank-account.service';
import { CurrencyTypeService } from '../../services/currency-type.service';
import { MatDialog, MatSnackBar } from '@angular/material';
import { CurrencyTypeEditComponent } from '../misc/currency-type-edit/currency-type-edit.component';
import { CurrencyType } from '../../models/currency-type';
import { BankAccountTypeEditComponent } from '../misc/bank-account-type-edit/bank-account-type-edit.component';
import { CreditService } from '../../services/credit.service';
import { CreditStatus } from '../../models/credit';

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
    { data: [], label: 'M' }
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
  currencyTypesColumns = ['name', 'exchangeRate', 'edit'];

  creditChartType: string = 'doughnut';
  creditChartDatasets: Array<any> = [
    { data: [], label: '' }
  ];
  creditChartLabels: Array<any> = [];
  creditChartColors: Array<any> = [
    {
      backgroundColor: ['#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
      hoverBackgroundColor: ['#FF5A5E', '#5AD3D1', '#FFC870', '#A8B3C5', '#616774'],
      borderWidth: 2,
    }
  ];
  creditStatuses: CreditStatus[];
  balanceCount: number;

  constructor(private bankAccountTypeService: BankAccountTypeService,
    private currencyTypeService: CurrencyTypeService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private creditService: CreditService,
    private bankAccountService: BankAccountService) { }

  ngOnInit() {
    this.fetchBankAccountTypes(true);
    this.fetchCurrencyTypes();
    this.fetchCreditTypes();
  }

  fetchBankAccountTypes(updateChart: boolean) {
    this.bankAccountTypeService.findAll()
      .subscribe(res => {
        this.bankAccountTypes = res;
        this.bankAccountTypes.sort((o1, o2) => o1.bankAccountType.localeCompare(o2.bankAccountType));
        if (updateChart) {
          this.fillChartData();
        }
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
  chartOptions: any;
  creditChartOptions: any;
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

  openBankAccTypeEditDialog(bankAccTypeId: any) {
    if (this.bankAccountTypes.some(e => e.id === bankAccTypeId)) {
      const dialogRef = this.dialog.open(BankAccountTypeEditComponent, {
        width: window.innerWidth > 768 ? '50%' : '85%',
        data: { id: bankAccTypeId }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          if (result.transactionComission < 0 || result.transactionComission > 99 ||
            result.exchangeCurrencyCommission < 0 || result.exchangeCurrencyCommission > 99) {
            this.snackBar.open('Wysokość prowizji musi być większa lub równa 0 i mniejsza niż 100', '', { duration: 10000, panelClass: 'red-snackbar' });
            return;
          }
          this.bankAccountTypeService.update(bankAccTypeId, result)
            .subscribe(res => this.fetchBankAccountTypes(false));
        }
      });
    }
  }

  fetchCreditTypes() {
    this.creditService.findAllCreditStatuses().subscribe(res => {
      this.creditStatuses = res;
      this.fillCreditChartData();
    });
  }

  fillCreditChartData() {
    let typesCounts: number[] = [];
    let typesNames: string[] = [];
    this.creditStatuses.forEach(e => {
      this.creditService.countAllByCreditType(e.creditType).subscribe(res => {
        this.creditChartDatasets[0].data.push(res);
        this.creditChartLabels.push(this.formatCreditType(e.creditType));
      });
    });
  }

  formatCreditType(creditType: string) {
    switch (creditType) {
      case 'ACTIVE':
        return 'Aktywny';
      case 'AWAITING':
        return 'Oczekujący';
      case 'CANCELED':
        return 'Odrzucony';
      case 'PAID':
        return 'Spłacony';
    }
  }

}
