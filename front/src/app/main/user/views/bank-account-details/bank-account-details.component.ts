import { Component, OnInit, ViewChild } from '@angular/core';
import { BankAccount } from '../../../models/bank-account';
import { BankAccountService } from '../../../services/bank-account.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Transaction } from '../../../models/transaction';
import { TransactionService } from '../../../services/transaction.service';
import { MatTableDataSource, MatPaginator, MatSort, MatSnackBar } from '@angular/material';
import { TransactionHistory } from '../../../models/transaction-history';
import { TransactionIn } from '../../../models/history-element';
import { faArrowCircleDown, faArrowCircleUp } from '@fortawesome/free-solid-svg-icons';
import { PaymentService } from '../../../services/payment.service';
import { Payment } from '../../../models/payment';
import { PaymentHistory } from '../../../models/payment-history';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-bank-account-details',
  templateUrl: './bank-account-details.component.html',
  styleUrls: ['./bank-account-details.component.scss']
})
export class BankAccountDetailsComponent implements OnInit {


  bankAccount: BankAccount;
  id: number;
  isLoading = true;
  isLoadingPayments = true;
  deleteForm: FormGroup;

  chartType: string = 'bar';
  chartDatasets: Array<any> = [
    { data: [0, 0, 0, 0, 0, 0], label: 'Salda' }
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

  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  chartOptions: any = {
    responsive: true
  };

  public chartClicked(e: any): void { /* console.log(e.active[0]._index)  */ }
  public chartHovered(e: any): void { }


  constructor(private bankAccountService: BankAccountService,
    private transactionService: TransactionService,
    private paymentService: PaymentService,
    private snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute) {
    this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
      this.bankAccountService.findById(this.id)
        .subscribe(res => {
          this.bankAccount = res;
          this.fillChartData();
          // transactionType to przelew/ wplata itp
          this.historyColumns = this.bankAccount.bankAccType.bankAccountType === 'MULTI_CURRENCY'
            ? ['title', 'transactionType', 'sourceAccNr', 'destAccNr', 'date', 'balance']
            : ['title', 'transactionType', 'sourceAccNr', 'destAccNr', 'date', 'balance'];

          this.transactionService.findAllByBankAccountId(this.id)
            .subscribe(res => {
              this.isLoading = false;
              let resMapped: TransactionIn[];
              resMapped = res.map(e => new TransactionIn(e, this.bankAccount.bankAccType.bankAccountType));

              resMapped.forEach(e => this.transactions.data.push(e));
              this.paymentService.findAllByBankAccountId(this.id)
                .subscribe(res => {
                  this.isLoadingPayments = false;
                  let resMapped: PaymentHistory[];
                  resMapped = res.map(e => new PaymentHistory(e));

                  resMapped.forEach(e => this.transactions.data.push(e));

                  this.transactions.data.sort((o1, o2) => new Date(o2.date).getTime() - new Date(o1.date).getTime());
                  this.transactions.paginator = this.paginator;
                });
            });


        });
    });

    this.initDeleteForm();
  }

  ngOnInit(): void {
  }

  fillChartData() {
    this.bankAccount
      .saldos
      .sort((o1, o2) => o1.currencyType.name.localeCompare(o2.currencyType.name));

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

  initDeleteForm() {
    this.deleteForm = this.fb.group({
      confirmNumber: ['', Validators.required]
    })
  }

  deleteBankAccount() {
    let confirmNumber = this.deleteForm.get('confirmNumber').value;

    if (confirmNumber) {
      confirmNumber = confirmNumber.replace(' ', '');

      if (confirmNumber !== this.bankAccount.number) {
        this.snackBar.open('Wprowadź poprawny numer rachunku', '', { duration: 3000, panelClass: 'red-snackbar' });
        return;
      }

      if (this.hasPositiveBalance()) {
        this.snackBar.open('Conajmniej jedno saldo posiada środki', '', { duration: 3000, panelClass: 'red-snackbar' });
        return;
      }

      this.bankAccountService.deleteById(this.bankAccount.id).subscribe(res => {
        this.snackBar.open('Rachunek został usunięty', '', { duration: 3000, panelClass: 'green-snackbar' });
        this.router.navigateByUrl('/core/bankAccounts');
      }, err =>
        this.snackBar.open(err.error.message, '', { duration: 3000, panelClass: 'red-snackbar' })
      );
    }
  }

  hasPositiveBalance() {
    return this.bankAccount.saldos.some(e => e.balance > 0);
  }
}
