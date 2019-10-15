import { Component, OnInit, ViewChild } from '@angular/core';
import { Credit } from '../../models/credit';
import { CreditService } from '../../services/credit.service';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-credit-list',
  templateUrl: './credit-list.component.html',
  styleUrls: ['./credit-list.component.scss']
})
export class CreditListComponent implements OnInit {

  credit: Credit;
  credits = new MatTableDataSource<Credit>();
  isLoading = true;
  searchForm: FormGroup;

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  creditsColumns = ['id', 'totalBalance', 'totalInstallmentCount', 'accept', 'reject'];

  constructor(private creditService: CreditService,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.fetchCreditList();
    this.createSearchForm();
  }
  
  createSearchForm() {
    this.searchForm = this.fb.group({
      identifier: ['']
    });
  }

  fetchCreditList() {
    this.creditService.findAllByCreditType('AWAITING')
      .subscribe(res => {
        this.credits.data = res;
        this.credits.paginator = this.paginator;
        this.isLoading = false;
      });
  }

  acceptCredit(id: number) {
    this.creditService.changeStatus(id, 'ACTIVE')
      .subscribe(res => this.fetchCreditList());
  }

  rejectCredit(id: number) { 
    this.creditService.changeStatus(id, 'CANCELED')
      .subscribe(res => this.fetchCreditList());
  }

  searchCredit() {
    const id = this.searchForm.get('id').value;
    if (!id) {
      return;
    }

    this.creditService.findById(id)
      .subscribe(res => this.credit = res, err => alert('Kredyt nie odnaleziony'));
  }

}
