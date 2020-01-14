import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { InvestmentListComponent } from '../investment-list/investment-list.component';
import { Investment } from '../../../models/investment';
import { InvestmentService } from '../../../services/investment.service';
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
    public investmentService: InvestmentService,
    private snackBar: MatSnackBar,
  ) {
  }

  ngOnInit() {
  }

  calculateProfitPercentage(): number {
    return ((this.data.currentBalance - this.data.startBalance) / this.data.startBalance) * 100;
  }

  closeInvestment() {
    this.investmentService.updateStatus(this.data.id)
      .subscribe(res => {
        this.snackBar.open('Pomyślnie zamknięto lokatę', '', { duration: 3000, panelClass: 'green-snackbar' });
        this.data = res;
      }, err =>
      this.snackBar.open(err.error.message, '', { duration: 3000, panelClass: 'red-snackbar' })
      );
  }
}
