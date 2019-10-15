import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatRadioChange } from '@angular/material';
import { TransactionTemplate } from '../../models/transaction-template';
import { BankAccountService } from '../../services/bank-account.service';
import { BankAccount } from '../../models/bank-account';
import { TransactionTemplateService } from '../../services/transaction-template-service.service';

export interface DialogDTO {
  templateId: number;
}

@Component({
  selector: 'app-dialog-window',
  templateUrl: './dialog-window.component.html',
  styleUrls: ['./dialog-window.component.scss']
})
export class DialogWindowComponent implements OnInit {

  bankAccounts: BankAccount[];
  standardType: boolean;
  title: string;
  template: TransactionTemplate;
  currencyList: string[];
  bankAccountsForCombobox: BankAccount[];

  constructor(
    public dialogRef: MatDialogRef<DialogWindowComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogDTO,
    private bankAccountService: BankAccountService,
    private transactiontemplateService: TransactionTemplateService) {

    if (data.templateId) {
      this.transactiontemplateService.findOneById(data.templateId).subscribe(res => {
        this.template = res;
        this.title = 'Edycja przelewu zdefiniowanego';
      });
    } else {
      this.template = new TransactionTemplate();
      this.template.multiCurrency = false;
      this.template.destinedCurrency = 'PLN';
      this.template.sourceCurrency = 'PLN';
      this.title = 'Tworzenie przelewu zdefiniowanego';
    }

    this.bankAccountService.findByUser().subscribe(res => {
      this.bankAccounts = res;
      this.fillBankAccounts(this.template.multiCurrency);
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

  changeSourceDestCurrencyType($event: MatRadioChange) {
    if ($event) {
      if (!this.template.multiCurrency) {
        this.fillBankAccounts(false);

      } else {
        this.fillBankAccounts(true);
      }
      this.template.destinedCurrency = 'PLN';
      this.template.sourceCurrency = 'PLN';
    }
  }

  fillBankAccounts(onlyMultiCurrency: boolean) {
    if (onlyMultiCurrency) {
      this.bankAccountsForCombobox = this.bankAccounts
        .filter(e => e.bankAccType.bankAccountType === 'MULTI_CURRENCY');
    } else {
      this.bankAccountsForCombobox = this.bankAccounts
        .filter(e => e.bankAccType.bankAccountType !== 'MULTI_CURRENCY');
    }
    this.changeCurrencyList();
  }

  changeCurrencyList() {
    if (this.template.sourceAccountNumber) {
      this.currencyList = this.bankAccounts
        .find(e => e.number === this.template.sourceAccountNumber)
        .saldos
        .map(e => String(e.currencyType.name));
    }
  }
}