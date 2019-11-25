import { Component, OnInit, Inject } from '@angular/core';
import { UserEditDialogComponent, UserModalDTO } from '../user-edit-dialog/user-edit-dialog.component';
import { BankAccount } from 'src/app/main/models/bank-account';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { BankAccountService } from 'src/app/main/services/bank-account.service';
import { Credit } from 'src/app/main/models/credit';
import { CreditService } from 'src/app/main/services/credit.service';
import { Investment } from 'src/app/main/models/investment';
import { InvestmentService } from 'src/app/main/services/investment.service';

@Component({
  selector: 'app-bank-account-dialog',
  templateUrl: './bank-account-dialog.component.html',
  styleUrls: ['./bank-account-dialog.component.scss']
})
export class BankAccountDialogComponent implements OnInit {

  account: BankAccount;
  credits: Credit[];
  investments: Investment[];
  id: any;

  constructor(
    public dialogRef: MatDialogRef<UserEditDialogComponent>,
    private bankAccountService: BankAccountService,
    private creditService: CreditService,
    private snackBar: MatSnackBar,
    private investmentService: InvestmentService,
    @Inject(MAT_DIALOG_DATA) public data: UserModalDTO, ) {
    if (data.id) {
      this.id = data.id;
      bankAccountService.findById(data.id)
        .subscribe(res => this.account = res);
      this.fetchCredits();
      this.fetchInvestments();
    }

  }

  ngOnInit() {
  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }

  fetchCredits() {
    this.creditService.findAllActiveBySaldoId(this.id)
      .subscribe(res => this.credits = res);
  }

  changeCreditStatus(id: any) {
    this.creditService.changeStatus(id, 'CANCELED')
      .subscribe(res => this.fetchCredits());
  }

  fetchInvestments() {
    this.investmentService.findAllActiveBySaldoId(this.id)
      .subscribe(res => this.investments = res);
  }

  changeInvestmentStatus(id: any) {
    this.investmentService.updateStatus(id)
      .subscribe(res => this.fetchInvestments());
  }

  deleteBankAccount() {
    this.bankAccountService.deleteById(this.id)
      .subscribe(res => {
        this.dialogRef.close();
        this.snackBar.open('Usunięto', '', { duration: 3000, panelClass: 'green-snackbar' });
      }, err=>{
        this.snackBar.open(err.error.message, '', { duration: 5000, panelClass: 'red-snackbar' });
     
      });
  }
}
