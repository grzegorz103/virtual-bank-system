import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { IndexComponent } from './index/index.component';
import { SharedComponent } from './shared.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { MatCardModule, MatButtonModule } from '@angular/material';


@NgModule({
  declarations: [
    IndexComponent,
    SharedComponent,
    RegisterComponent,
    LoginComponent,
  ],
  imports: [
    CommonModule,
    SharedRoutingModule,
    MatCardModule,
    MatButtonModule
  ]
})
export class SharedModule { }
