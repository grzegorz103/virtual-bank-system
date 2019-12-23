import { Component, OnInit, ViewChild } from '@angular/core';
import { Credit } from '../../../models/credit';
import { CreditService } from '../../../services/credit.service';
import { ActivatedRoute } from '@angular/router';
import { InstallmentService } from 'src/app/main/services/installment.service';
import { Installment } from '../../../models/installment';
import { MatTableDataSource, MatPaginator, MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-credit-details',
  templateUrl: './credit-details.component.html',
  styleUrls: ['./credit-details.component.scss']
})
export class CreditDetailsComponent implements OnInit {

  credit: Credit;
  installments = new MatTableDataSource<Installment>();
  creditId: any;
  isLoading = true;
  installmentColumns = [/* 'id', */ 'amount', 'payDate'];

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  constructor(private creditService: CreditService,
    private installmentService: InstallmentService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute) {
    this.creditId = this.route.snapshot.paramMap.get('id');
  }

  ngOnInit() {
    this.fetchCredit();
    this.fetchInstallmentList();
  }

  fetchCredit() {
    this.creditService.findById(this.creditId)
      .subscribe(res => this.credit = res);
  }

  fetchInstallmentList() {
    this.installmentService.findAllByCreditId(this.creditId)
      .subscribe(res => {
        this.installments.data = res.sort((o1, o2) => new Date(o1.payDate).getTime() - new Date(o2.payDate).getTime());
        this.isLoading = false;
        this.installments.paginator = this.paginator;
      });
  }

  createInstallment() {
    this.installmentService.create({
      sourceSaldoId: this.credit.destinedSaldoId,
      currency: this.credit.currency,
      creditId: this.credit.id
    }).subscribe(res => {
      this.fetchInstallmentList();
      this.fetchCredit();
      this.snackBar.open('Utworzono wpłatę', '', { duration: 3000, panelClass: 'green-snackbar' });
    });
  }

}
