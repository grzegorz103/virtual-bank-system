import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BankAccountListComponent } from './main/views/bank-account-list/bank-account-list.component';
import { BankAccountService } from './main/services/bank-account.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { TransactionComponent } from './main/views/transaction/transaction.component';
import { TransactionMultiCurrencyComponent } from './main/views/transaction-multi-currency/transaction-multi-currency.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ExchangeCurrencyComponent } from './main/views/exchange-currency/exchange-currency.component';

@NgModule({
  declarations: [
    AppComponent,
    BankAccountListComponent,
    TransactionComponent,
    TransactionMultiCurrencyComponent,
    NavbarComponent,
    ExchangeCurrencyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [BankAccountService],
  bootstrap: [AppComponent]
})
export class AppModule { }
