import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { IndexComponent } from './index/index.component';
import { SharedComponent } from './shared.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { MatCardModule, MatButtonModule, MatToolbarModule, MatInputModule, MatDatepickerModule, MatNativeDateModule, MatSnackBarModule, MatExpansionModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './services/auth.service';
import { FooterComponent } from './footer/footer.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";


@NgModule({
  declarations: [
    IndexComponent,
    SharedComponent,
    RegisterComponent,
    FooterComponent,
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
    MatProgressSpinnerModule,
    MatExpansionModule
  ],
  providers: []
})
export class SharedModule { }
