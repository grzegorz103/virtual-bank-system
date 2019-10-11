import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeRoutingModule } from './employee-routing.module';
import { EmployeeComponent } from './employee.component';
import { SummaryComponent } from './summary/summary.component';
import { MatPaginatorModule, MatCardModule, MatFormFieldModule, MatToolbarModule, MatTableModule, MatButtonModule, MatInputModule, MatAutocompleteModule, MatOptionModule, MatSelectModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { EmployeeSupportComponent } from './employee-support/employee-support.component';
import { MainModule } from '../main.module';
import { PaymentCreateComponent } from './payment-create/payment-create.component';
import { UserListComponent } from './user-list/user-list.component';

@NgModule({
  declarations: [
    EmployeeComponent,
    SummaryComponent,
    EmployeeSupportComponent,
    PaymentCreateComponent,
    UserListComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    EmployeeRoutingModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatButtonModule,
    MatTableModule,
    MatAutocompleteModule,
    MatSelectModule,
    MatOptionModule
  ]
})
export class EmployeeModule { }
