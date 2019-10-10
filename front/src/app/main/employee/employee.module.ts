import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeRoutingModule } from './employee-routing.module';
import { EmployeeComponent } from './employee.component';
import { SummaryComponent } from './summary/summary.component';
import { MatPaginatorModule, MatCardModule, MatFormFieldModule, MatToolbarModule, MatTableModule, MatButtonModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { EmployeeSupportComponent } from './employee-support/employee-support.component';
import { ConversationDetailsComponent } from './conversation-details/conversation-details.component';


@NgModule({
  declarations: [
    EmployeeComponent,
    SummaryComponent,
    EmployeeSupportComponent,
    ConversationDetailsComponent
  ],
  imports: [
    CommonModule,
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
