import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BankAccountListComponent } from './views/bank-account-list/bank-account-list.component';
import { TransactionComponent } from './views/transaction/transaction.component';
import { TransactionMultiCurrencyComponent } from './views/transaction-multi-currency/transaction-multi-currency.component';
import { TemplateListComponent } from './views/transaction-templates/template-list/template-list.component';
import { TemplateCreateComponent } from './views/transaction-templates/template-create/template-create.component';
import { BankAccountDetailsComponent } from './views/bank-account-details/bank-account-details.component';
import { ExchangeCurrencyComponent } from './views/exchange-currency/exchange-currency.component';
import { CreditCreateComponent } from './views/credit-create/credit-create.component';
import { MainComponent } from './main.component';


const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children:
      [
        { path: 'bankAccounts', component: BankAccountListComponent },
        { path: 'transactions/create', component: TransactionComponent },
        { path: 'transactions/create/multicurrency', component: TransactionMultiCurrencyComponent },
        { path: 'transactions/defined', component: TemplateListComponent },
        { path: 'transactions/defined/create', component: TemplateCreateComponent },
        { path: 'bankAccounts/:id/details', component: BankAccountDetailsComponent },
        { path: 'exchange', component: ExchangeCurrencyComponent },
        { path: 'credits/create', component: CreditCreateComponent },
      ]
  }]
  ;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
