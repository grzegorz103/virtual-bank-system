import { Component, OnInit, Inject } from '@angular/core';
import { UserEditDialogComponent, UserModalDTO } from '../user-edit-dialog/user-edit-dialog.component';
import { BankAccount } from 'src/app/main/models/bank-account';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BankAccountService } from 'src/app/main/services/bank-account.service';
import { Credit } from 'src/app/main/models/credit';
import { CreditService } from 'src/app/main/services/credit.service';

@Component({
  selector: 'app-bank-account-dialog',
  templateUrl: './bank-account-dialog.component.html',
  styleUrls: ['./bank-account-dialog.component.scss']
})
export class BankAccountDialogComponent implements OnInit {

  account: BankAccount;
  credits: Credit[];

  constructor(
    public dialogRef: MatDialogRef<UserEditDialogComponent>,
    private bankAccountService: BankAccountService,
    private creditService: CreditService,
    @Inject(MAT_DIALOG_DATA) public data: UserModalDTO, ) {
    if (data.id) {
      bankAccountService.findById(data.id)
        .subscribe(res => this.account = res);
      creditService.findAllActiveBySaldoId(data.id)
        .subscribe(res => this.credits = res);
    }

  }

  ngOnInit() {
  }

}
