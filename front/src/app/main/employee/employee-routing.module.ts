import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeComponent } from './employee.component';
import { SummaryComponent } from './summary/summary.component';


const routes: Routes = [
  {
    path: '',
    component: EmployeeComponent,
    children:
      [
        { path: 'summary', component: SummaryComponent },
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
