import { Component, OnInit, Inject } from '@angular/core';
import { CurrencyType } from 'src/app/main/models/currency-type';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EmployeeAddComponent } from '../employee-add/employee-add.component';
import { CurrencyTypeService } from 'src/app/main/services/currency-type.service';

export interface CurrencyTypeDto{
  id: any;
}

@Component({
  selector: 'app-currency-type-edit',
  templateUrl: './currency-type-edit.component.html',
  styleUrls: ['./currency-type-edit.component.scss']
})
export class CurrencyTypeEditComponent implements OnInit {

  currencyType: CurrencyType;
  
  constructor(
    public dialogRef: MatDialogRef<EmployeeAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CurrencyTypeDto,
    private currencyTypeService: CurrencyTypeService) {

    if (data.id) {
      this.currencyTypeService.findById(data.id)
        .subscribe(res => this.currencyType = res);
    };
  }


  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }
}
