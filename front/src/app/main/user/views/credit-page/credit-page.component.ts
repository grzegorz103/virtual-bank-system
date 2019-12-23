import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSnackBar } from '@angular/material';
import { Credit } from '../../../models/credit';
import { CreditService } from '../../../services/credit.service';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-credit-page',
  templateUrl: './credit-page.component.html',
  styleUrls: ['./credit-page.component.scss']
})
export class CreditPageComponent implements OnInit {

  credits = new MatTableDataSource<Credit>();
  isLoading = true;

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  creditsColumns = [/* 'id', */ 'totalBalance', 'totalInstallmentCount', 'currency', 'status', 'details'];

  constructor(private creditService: CreditService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.fetchCreditList();
  }

  fetchCreditList() {
    this.creditService.findAllByCurrentUser()
      .subscribe(res => {
        this.credits.data = res;
        this.credits.paginator = this.paginator;
        this.isLoading = false;
      });
  }
}