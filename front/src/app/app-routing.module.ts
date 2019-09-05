import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BankAccountListComponent } from './main/views/bank-account-list/bank-account-list.component';
import { TransactionComponent } from './main/views/transaction/transaction.component';
import { TransactionMultiCurrencyComponent } from './main/views/transaction-multi-currency/transaction-multi-currency.component';


const routes: Routes = [
  {path: 'bankAccounts', component: BankAccountListComponent},
  {path: 'transactions/create', component: TransactionComponent},
  {path: 'transactions/create/multicurrency', component: TransactionMultiCurrencyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
