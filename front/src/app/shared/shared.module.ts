import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { IndexComponent } from './index/index.component';
import { SharedComponent } from './shared.component';


@NgModule({
  declarations: [
    IndexComponent,
    SharedComponent
  ],
  imports: [
    CommonModule,
    SharedRoutingModule
  ]
})
export class SharedModule { }
