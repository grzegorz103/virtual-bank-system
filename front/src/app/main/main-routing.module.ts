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
import { CoreGuardService } from './misc/guard/core-guard.service';
import { UserSupportComponent } from './user-support/user-support.component';
import { ConversationDetailsComponent } from './conversation-details/conversation-details.component';
import { ProfileEditComponent } from './profile-edit/profile-edit.component';
import { ExchangePageComponent } from './views/exchange-page/exchange-page.component';
import { CreditPageComponent } from './views/credit-page/credit-page.component';
import { CreditDetailsComponent } from './views/credit-details/credit-details.component';
import { InvestmentListComponent } from './views/investment-list/investment-list.component';


const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children:
      [
        {
          path: 'bankAccounts', component: BankAccountListComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'transactions/create', component: TransactionComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'transactions/create/multicurrency', component: TransactionMultiCurrencyComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'transactions/defined', component: TemplateListComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'transactions/defined/create', component: TemplateCreateComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'bankAccounts/:id/details', component: BankAccountDetailsComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'exchange', component: ExchangePageComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'credits/create', component: CreditCreateComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'support', component: UserSupportComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'profile', component: ProfileEditComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'credits/list', component: CreditPageComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'credits/:id/details', component: CreditDetailsComponent,
          canActivate: [CoreGuardService]
        },
        {
          path: 'investments/list', component: InvestmentListComponent,
          canActivate: [CoreGuardService]
        },
       // {path: 'excha'}
        {
          path: 'conversation/:id', component: ConversationDetailsComponent
        },
        {
          path: 'admin', loadChildren: './admin/admin.module#AdminModule',
        },
        {
          path: 'employee', loadChildren: './employee/employee.module#EmployeeModule',
        }
      ],
  }]
  ;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
