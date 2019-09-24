import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedComponent } from './shared.component';
import { IndexComponent } from './index/index.component';


const routes: Routes = [
  {
    path: '',
    component: SharedComponent,
    children:
      [
        { path: '**', component: IndexComponent }, 
      ]
  }]
  ;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SharedRoutingModule { }
