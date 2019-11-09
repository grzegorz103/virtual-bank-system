import { Component, OnInit, Inject } from '@angular/core';
import { BankAccType } from 'src/app/main/models/bank-acc-type';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EmployeeAddComponent } from '../employee-add/employee-add.component';
import { BankAccountTypeService } from 'src/app/main/services/bank-account-type.service';

export interface BankAccountTypeEditDto{
  id: any;
}

@Component({
  selector: 'app-bank-account-type-edit',
  templateUrl: './bank-account-type-edit.component.html',
  styleUrls: ['./bank-account-type-edit.component.scss']
})
export class BankAccountTypeEditComponent implements OnInit {

  bankAccType: BankAccType;
  
  constructor(
    public dialogRef: MatDialogRef<EmployeeAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: BankAccountTypeEditDto,
    private bankAccountTypeService: BankAccountTypeService) {

    if (data.id) {
      this.bankAccountTypeService.findById(data.id)
        .subscribe(res => this.bankAccType = res);
    }
  }


  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
