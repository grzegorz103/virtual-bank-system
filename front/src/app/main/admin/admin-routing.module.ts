import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';
import { StatisticsComponent } from './statistics/statistics.component';


const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children:
      [
        { path: 'stat', component: StatisticsComponent },
      ],
  }]
  ;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
