import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { SupportListComponent } from './support-list/support-list.component';
import { ConversationDetailsComponent } from '../conversation-details/conversation-details.component';


const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children:
      [
        { path: 'stat', component: StatisticsComponent },
        { path: 'employees', component: EmployeeListComponent },
        { path: 'support', component: SupportListComponent },
        { path: 'conversation/:id', component: ConversationDetailsComponent }
      ],
  }]
  ;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
