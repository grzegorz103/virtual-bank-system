import { Component, OnInit, ViewChild } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountService } from '../../services/bank-account.service';
import { ActivatedRoute } from '@angular/router';
import { Transaction } from '../../models/transaction';
import { TransactionService } from '../../services/transaction.service';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { TransactionHistory } from '../../models/transaction-history';
import { TransactionIn } from '../../models/history-element';
import { faArrowCircleDown, faArrowCircleUp } from '@fortawesome/free-solid-svg-icons';
class A implements TransactionHistory {
  id = 2;
  dupa = 'asd'
}
@Component({
  selector: 'app-bank-account-details',
  templateUrl: './bank-account-details.component.html',
  styleUrls: ['./bank-account-details.component.scss']
})
export class BankAccountDetailsComponent implements OnInit {


  bankAccount: BankAccount;
  id: number;
  isLoading = true;

  chartType: string = 'bar';
  chartDatasets: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55], label: 'Salda' }
  ];

  faArrowCircleUp = faArrowCircleUp;
  faArrowCircleDown = faArrowCircleDown;

  transactions = new MatTableDataSource<TransactionHistory>();
  historyColumns: string[];

  chartLabels: Array<any> = ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'];
  chartColors: Array<any> = [
    {
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
      borderWidth: 2,
    }
  ];

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  @ViewChild(MatSort, {static: true})
   sort: MatSort;

  chartOptions: any = {
    responsive: true
  };

  public chartClicked(e: any): void { /* console.log(e.active[0]._index)  */ }
  public chartHovered(e: any): void { }


  constructor(private bankAccountService: BankAccountService,
    private transactionService: TransactionService,
    private activatedRoute: ActivatedRoute) {
    this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
      this.bankAccountService.findById(this.id)
        .subscribe(res => {
          this.bankAccount = res;
          this.fillChartData();
          // transactionType to przelew/ wplata itp
          this.historyColumns = this.bankAccount.bankAccType.bankAccountType === 'MULTI_CURRENCY'
            ? ['id', 'transactionType', 'sourceAccNr', 'destAccNr', 'date', 'balance']
            : ['id', 'transactionType', 'sourceAccNr', 'destAccNr', 'date', 'balance', 'sourceCurrency', 'destCurrency'];

          this.transactionService.findAllByBankAccountId(this.id)
            .subscribe(res => {
              this.isLoading = false;
              let resMapped: TransactionIn[];
              resMapped = res.map(e => new TransactionIn(e, this.bankAccount.bankAccType.bankAccountType));

              resMapped.forEach(e => this.transactions.data.push(e));
              this.transactions.data.push(new A());
              this.transactions.paginator = this.paginator;
            });
        });

    })
  }

  ngOnInit(): void {
  }

  fillChartData() {
    let saldos = this.bankAccount.saldos.map(e => e.balance);
    this.chartDatasets = [{ data: saldos, label: 'Stan konta' }];

    let saldoNames = this.bankAccount.saldos.map(e => e.currencyType.name);
    this.chartLabels = saldoNames;

    this.chartOptions = {
      responsive: true,
      scales: {
        xAxes: [{ barPercentage: this.bankAccount.saldos.length / 5 }],
        yAxes: [{
          display: true,
          ticks: {
            beginAtZero: true
          }
        }]
      }
    }
  }

  getObjectType(object) {
    return object.constructor.name;
  }

}
