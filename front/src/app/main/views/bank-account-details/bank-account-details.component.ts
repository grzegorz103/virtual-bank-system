import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountService } from '../../services/bank-account.service';
import { ActivatedRoute } from '@angular/router';
import { Transaction } from '../../models/transaction';
import { TransactionService } from '../../services/transaction.service';
import { MatTableDataSource } from '@angular/material';
import { TransactionHistory } from '../../models/transaction-history';

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

  chartType: string = 'bar';
  chartDatasets: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55], label: 'Salda' }
  ];

  transactions: MatTableDataSource<TransactionHistory>;
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
          console.log(this.bankAccount.bankAccType.bankAccountType);
        });

      this.transactionService.findAllByBankAccountId(this.id)
        .subscribe(res => {
          this.transactions = new MatTableDataSource<TransactionHistory>(res as Transaction[]);
          this.transactions.data.push(new A());
          this.transactions.data.forEach(e => console.log(typeof (e)))
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

}
