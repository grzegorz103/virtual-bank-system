import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeComponent } from './employee.component';
import { SummaryComponent } from './summary/summary.component';
import { EmployeeSupportComponent } from './employee-support/employee-support.component';
import { ConversationDetailsComponent } from '../conversation-details/conversation-details.component';
import { PaymentCreateComponent } from './payment-create/payment-create.component';
import { UserListComponent } from './user-list/user-list.component';

const routes: Routes = [
  {
    path: '',
    component: EmployeeComponent,
    children:
      [
        { path: 'summary', component: SummaryComponent },
        { path: 'support', component: EmployeeSupportComponent },
        { path: 'users', component: UserListComponent},
        { path: 'payment/create', component: PaymentCreateComponent}
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
