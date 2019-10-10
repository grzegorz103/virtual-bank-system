import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeRoutingModule } from './employee-routing.module';
import { EmployeeComponent } from './employee.component';
import { SummaryComponent } from './summary/summary.component';
import { MatPaginatorModule, MatCardModule, MatFormFieldModule, MatToolbarModule, MatTableModule, MatButtonModule, MatInputModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { EmployeeSupportComponent } from './employee-support/employee-support.component';
import { MainModule } from '../main.module';

@NgModule({
  declarations: [
    EmployeeComponent,
    SummaryComponent,
    EmployeeSupportComponent,
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
    MatTableModule
  ]
})
export class EmployeeModule { }
