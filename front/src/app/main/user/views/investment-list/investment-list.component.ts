import { Component, OnInit, ViewChild } from '@angular/core';
import { InvestmentService } from '../../../services/investment.service';
import { Investment } from '../../../models/investment';
import { MatTableDataSource, MatPaginator, MatDialog } from '@angular/material';
import { InvestmentDetailsComponent } from '../investment-details/investment-details.component';

@Component({
  selector: 'app-investment-list',
  templateUrl: './investment-list.component.html',
  styleUrls: ['./investment-list.component.scss']
})
export class InvestmentListComponent implements OnInit {

  columns = [/* 'ID', */ 'date', 'currentBalance', 'investmentType', 'details'];

  investments: MatTableDataSource<Investment>;

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  constructor(private investmentService: InvestmentService,
    public dialog: MatDialog) { }

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

  openDialog(index: any) {

    const dialogRef = this.dialog.open(InvestmentDetailsComponent, {
      width: '50%',
      data: this.investments.data.find(e => e.id === index)
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchInvestments();
    });
  }
}
