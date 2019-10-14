import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { IndexComponent } from './index/index.component';
import { SharedComponent } from './shared.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { MatCardModule, MatButtonModule, MatToolbarModule, MatInputModule, MatDatepickerModule, MatNativeDateModule, MatSnackBarModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './services/auth.service';


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
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],
  providers: []
})
export class SharedModule { }
