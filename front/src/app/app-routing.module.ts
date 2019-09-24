import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BankAccountListComponent } from './main/views/bank-account-list/bank-account-list.component';
import { TransactionComponent } from './main/views/transaction/transaction.component';
import { TransactionMultiCurrencyComponent } from './main/views/transaction-multi-currency/transaction-multi-currency.component';
import { ExchangeCurrencyComponent } from './main/views/exchange-currency/exchange-currency.component';
import { BankAccountDetailsComponent } from './main/views/bank-account-details/bank-account-details.component';
import { TemplateListComponent } from './main/views/transaction-templates/template-list/template-list.component';
import { TemplateCreateComponent } from './main/views/transaction-templates/template-create/template-create.component';
import { CreditCreateComponent } from './main/views/credit-create/credit-create.component';
import { IndexComponent } from './shared/index/index.component';


const routes: Routes = [
  { path: 'core', loadChildren: './main/main.module#MainModule' },
  { path: 'shared', loadChildren: './shared/shared.module#SharedModule' },
  { path: '**', redirectTo: 'shared'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
