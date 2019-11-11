import { Component, OnInit, ViewChild } from '@angular/core';
import { InvestmentService } from '../../services/investment.service';
import { Investment } from '../../models/investment';
import { MatTableDataSource, MatPaginator } from '@angular/material';

@Component({
  selector: 'app-investment-list',
  templateUrl: './investment-list.component.html',
  styleUrls: ['./investment-list.component.scss']
})
export class InvestmentListComponent implements OnInit {

  columns = ['ID', 'date', 'currentBalance', 'investmentType', 'details'];

  investments: MatTableDataSource<Investment>;

@ViewChild(MatPaginator, { static: true })
paginator: MatPaginator;

constructor(private investmentService: InvestmentService) { }

ngOnInit() {
  this.fetchInvestments();
}

fetchInvestments() {
  this.investmentService.findAllByUser()
    .subscribe(res => {
      this.investments = new MatTableDataSource<Investment>();
      this.investments.data = res;
      this.investments.paginator = this.paginator;
    });
}

}
