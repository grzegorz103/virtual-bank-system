import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BankAccountListComponent } from './main/views/bank-account-list/bank-account-list.component';
import { BankAccountService } from './main/services/bank-account.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TransactionComponent } from './main/views/transaction/transaction.component';
import { TransactionMultiCurrencyComponent } from './main/views/transaction-multi-currency/transaction-multi-currency.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ExchangeCurrencyComponent } from './main/views/exchange-currency/exchange-currency.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MatExpansionModule } from '@angular/material/expansion';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatStepperModule } from '@angular/material/stepper';
import { MatButtonModule, MatFormFieldModule, MatInputModule, MatCardModule, MatOptionModule, 
  MatSelectModule, MatIconModule, MatTableModule, MatProgressBar, MatProgressBarModule, MatDividerModule, MatDialogModule, MatTabsModule, MatRadioButton, MatRadioModule, MatToolbarModule } from '@angular/material';
import { BankAccountDetailsComponent } from './main/views/bank-account-details/bank-account-details.component';
import { ChartsModule, WavesModule, MDBBootstrapModule } from 'angular-bootstrap-md';
import { TemplateListComponent } from './main/views/transaction-templates/template-list/template-list.component';
import { TemplateCreateComponent } from './main/views/transaction-templates/template-create/template-create.component';
import { TemplateDetailsComponent } from './template-details/template-details.component';
import { DialogWindowComponent } from './main/misc/dialog-window/dialog-window.component';

@NgModule({
  declarations: [
    AppComponent,
    BankAccountListComponent,
    TransactionComponent,
    TransactionMultiCurrencyComponent,
    NavbarComponent,
    ExchangeCurrencyComponent,
    BankAccountDetailsComponent,
    TemplateListComponent,
    TemplateCreateComponent,
    TemplateDetailsComponent,
    DialogWindowComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
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
    MatIconModule,MatTableModule,
    ChartsModule,
    WavesModule,MatProgressBarModule,
    MatDividerModule,
    MatDialogModule,
    MatTabsModule,
    MatRadioModule,
    MatToolbarModule
  ],
  providers: [BankAccountService],
  entryComponents: [ DialogWindowComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
