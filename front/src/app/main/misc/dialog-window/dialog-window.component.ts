import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TransactionTemplate } from '../../models/transaction-template';
import { BankAccountService } from '../../services/bank-account.service';
import { BankAccount } from '../../models/bank-account';

@Component({
  selector: 'app-dialog-window',
  templateUrl: './dialog-window.component.html',
  styleUrls: ['./dialog-window.component.scss']
})
export class DialogWindowComponent implements OnInit {

  bankAccounts: BankAccount[];
  standardType: boolean;
  constructor(
    public dialogRef: MatDialogRef<DialogWindowComponent>,
    @Inject(MAT_DIALOG_DATA) public data: TransactionTemplate,
    private bankAccountService: BankAccountService) {
    this.bankAccountService.findAll().subscribe(res => this.bankAccounts = res);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit() {
  }


}
