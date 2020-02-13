import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent, getPolishPaginatorIntl } from './app.component';
import { BankAccountListComponent } from './main/user/views/bank-account-list/bank-account-list.component';
import { BankAccountService } from './main/services/bank-account.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TransactionComponent } from './main/user/views/transaction/transaction.component';
import { TransactionMultiCurrencyComponent } from './main/user/views/transaction-multi-currency/transaction-multi-currency.component';
import { NavbarComponent, PwaService } from './shared/navbar/navbar.component';
import { ExchangeCurrencyComponent } from './main/user/views/exchange-currency/exchange-currency.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MatExpansionModule } from '@angular/material/expansion';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatStepperModule } from '@angular/material/stepper';
import {
  MatButtonModule, MatFormFieldModule, MatInputModule, MatCardModule, MatOptionModule,
  MatSelectModule, MatIconModule, MatTableModule, MatProgressBar, MatProgressBarModule, MatDividerModule, MatDialogModule, MatTabsModule, MatRadioButton, MatRadioModule, MatToolbarModule, MatProgressSpinnerModule, MatCheckboxModule, MatSlideToggleModule, MatChipsModule, MatSnackBarModule, MatPaginatorIntl
} from '@angular/material';
import { BankAccountDetailsComponent } from './main/user/views/bank-account-details/bank-account-details.component';
import { ChartsModule, WavesModule, MDBBootstrapModule } from 'angular-bootstrap-md';
import { TemplateListComponent } from './main/user/views/transaction-templates/template-list/template-list.component';
import { TemplateCreateComponent } from './main/user/views/transaction-templates/template-create/template-create.component';
import { DialogWindowComponent } from './main/misc/dialog-window/dialog-window.component';
import { CreditCreateComponent } from './main/user/views/credit-create/credit-create.component';
import { Ng5SliderModule } from 'ng5-slider';
import { CreditService } from './main/services/credit.service';
import { IndexComponent } from './shared/index/index.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { JwtModule } from "@auth0/angular-jwt";
import 'moment/locale/pl';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
export function tokenGetter() {
  return localStorage.getItem("token");
}

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, MatCardModule,
    BrowserAnimationsModule,
    FormsModule,
    FontAwesomeModule,
    MatExpansionModule,
    ReactiveFormsModule,
    MatStepperModule, MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    MatCardModule,
    MatIconModule, MatTableModule,
    ChartsModule,
    WavesModule, MatProgressBarModule,
    MatDividerModule,
    MatChipsModule,
    MatDialogModule,
    MatTabsModule,
    MatRadioModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatToolbarModule,
    MatCheckboxModule,
    MatSlideToggleModule,
    Ng5SliderModule,
    MatTooltipModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: ['localhost:8080', 'https://virtual-sys.herokuapp.com' ,'virtual-sys.herokuapp.com'],
      }
    }),
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [PwaService, {provide: MatPaginatorIntl, useValue: getPolishPaginatorIntl()}],
  entryComponents: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
