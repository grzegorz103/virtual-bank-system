import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { InvestmentListComponent } from '../investment-list/investment-list.component';
import { Investment } from '../../models/investment';
import { InvestmentService } from '../../services/investment.service';
import { faChartLine } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-investment-details',
  templateUrl: './investment-details.component.html',
  styleUrls: ['./investment-details.component.scss']
})
export class InvestmentDetailsComponent implements OnInit {

  faChartLine = faChartLine;

  constructor(
    public dialogRef: MatDialogRef<InvestmentListComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Investment,
    public investmentService: InvestmentService
  ) {
  }

  ngOnInit() {
  }

  calculateProfitPercentage() : number{
    return ((this.data.currentBalance - this.data.startBalance) / this.data.startBalance) * 100;
  }
}
