import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../models/bank-account';
import { BankAccountService } from '../../services/bank-account.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bank-account-details',
  templateUrl: './bank-account-details.component.html',
  styleUrls: ['./bank-account-details.component.scss']
})
export class BankAccountDetailsComponent implements OnInit {

  bankAccount: BankAccount;
  id: number;

  public chartType: string = 'bar';

  public chartDatasets: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55], label: 'My First dataset' }
  ];

  public chartLabels: Array<any> = ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'];

  public chartColors: Array<any> = [
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

  public chartOptions: any = {
    responsive: true
  };
  public chartClicked(e: any): void { }
  public chartHovered(e: any): void { }


  constructor(private bankAccountService: BankAccountService,
    private activatedRoute: ActivatedRoute) {
    this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
      this.bankAccountService.findById(this.id)
        .subscribe(res => {this.bankAccount = res; this.fillChartData()});
    })
  }
  ngOnInit(): void {
  }

  fillChartData(){
    let saldos = this.bankAccount.saldos.map(e=>e.balance);
    this.chartDatasets = [{ data: saldos, label: 'Stan konta' }];

    let saldoNames = this.bankAccount.saldos.map(e=>e.currencyType.currency);
    this.chartLabels = saldoNames;
  }

}
