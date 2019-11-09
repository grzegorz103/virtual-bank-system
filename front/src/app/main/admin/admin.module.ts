import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { StatisticsComponent } from './statistics/statistics.component';
import { AdminComponent } from './admin.component';
import { MatToolbarModule, MatTableModule, MatButtonModule, MatFormFieldModule, MatDatepickerModule, MatNativeDateModule, MatInputModule, MatPaginatorModule, MatDialogModule } from '@angular/material';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { SupportListComponent } from './support-list/support-list.component';
import { ChartsModule, WavesModule } from 'angular-bootstrap-md';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { EmployeeAddComponent } from './misc/employee-add/employee-add.component';
import { EmployeeDetailsComponent } from './misc/employee-details/employee-details.component';
import { MomentModule } from 'angular2-moment';
import { CurrencyTypeEditComponent } from './misc/currency-type-edit/currency-type-edit.component';
import { BankAccountTypeEditComponent } from './misc/bank-account-type-edit/bank-account-type-edit.component';


@NgModule({
  declarations: [StatisticsComponent,
    EmployeeListComponent,
    SupportListComponent,
    EmployeeAddComponent, EmployeeDetailsComponent,
    CurrencyTypeEditComponent,
    BankAccountTypeEditComponent,
    AdminComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatTableModule, ChartsModule, WavesModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MomentModule,
    MatDatepickerModule,
    MatPaginatorModule,
    FormsModule, MatDialogModule,
    MatNativeDateModule, MatInputModule,
  ],
  entryComponents: [EmployeeAddComponent, EmployeeDetailsComponent, CurrencyTypeEditComponent, BankAccountTypeEditComponent]
})
export class AdminModule { }
