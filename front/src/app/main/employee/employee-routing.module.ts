import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeComponent } from './employee.component';
import { SummaryComponent } from './summary/summary.component';
import { EmployeeSupportComponent } from './employee-support/employee-support.component';
import { ConversationDetailsComponent } from './conversation-details/conversation-details.component';


const routes: Routes = [
  {
    path: '',
    component: EmployeeComponent,
    children:
      [
        { path: 'summary', component: SummaryComponent },
        { path: 'support', component: EmployeeSupportComponent },
        { path: 'conversation/:id', component: ConversationDetailsComponent }
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
