import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MainRoutingModule } from './main-routing.module';
import { MainComponent } from './main.component';
import { BankAccountListComponent } from './user/views/bank-account-list/bank-account-list.component';
import { TransactionComponent } from './user/views/transaction/transaction.component';
import { TransactionMultiCurrencyComponent } from './user/views/transaction-multi-currency/transaction-multi-currency.component';
import { NavbarComponent } from '../shared/navbar/navbar.component';
import { ExchangeCurrencyComponent } from './user/views/exchange-currency/exchange-currency.component';
import { BankAccountDetailsComponent } from './user/views/bank-account-details/bank-account-details.component';
import { TemplateListComponent } from './user/views/transaction-templates/template-list/template-list.component';
import { TemplateCreateComponent } from './user/views/transaction-templates/template-create/template-create.component';
import { DialogWindowComponent } from './misc/dialog-window/dialog-window.component';
import { CreditCreateComponent } from './user/views/credit-create/credit-create.component';
import { AppRoutingModule } from '../app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { MatCardModule, MatExpansionModule, MatStepperModule, MatButtonModule, MatFormFieldModule, MatInputModule, MatOptionModule, MatSelectModule, MatIconModule, MatTableModule, MatProgressBarModule, MatDividerModule, MatDialogModule, MatTabsModule, MatRadioModule, MatProgressSpinnerModule, MatToolbarModule, MatCheckboxModule, MatSlideToggleModule, MatTooltipModule, MatPaginatorModule, MatChipsModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ChartsModule, WavesModule } from 'angular-bootstrap-md';
import { Ng5SliderModule } from 'ng5-slider';
import { BankAccountService } from './services/bank-account.service';
import { CreditService } from './services/credit.service';
import { AuthService } from '../shared/services/auth.service';
import { UserSupportComponent } from './user/user-support/user-support.component';
import { MomentModule } from 'angular2-moment';
import { ExchangePageComponent } from './user/views/exchange-page/exchange-page.component';
import { CreditPageComponent } from './user/views/credit-page/credit-page.component';
import { CreditDetailsComponent } from './user/views/credit-details/credit-details.component';
import { InvestmentListComponent } from './user/views/investment-list/investment-list.component';
import { InvestmentDetailsComponent } from './user/views/investment-details/investment-details.component';
import { InvestmentCreateComponent } from './user/views/investment-create/investment-create.component';
import { ConversationDetailsComponent } from './common/conversation-details/conversation-details.component';
import { ProfileEditComponent } from './common/profile-edit/profile-edit.component';


@NgModule({
  declarations: [MainComponent,
    BankAccountListComponent,
    TransactionComponent,
    TransactionMultiCurrencyComponent,
    ExchangeCurrencyComponent,
    BankAccountDetailsComponent,
    TemplateListComponent,
    TemplateCreateComponent,
    DialogWindowComponent,
    CreditCreateComponent,
    ConversationDetailsComponent,
    UserSupportComponent,
    ExchangePageComponent,
    InvestmentListComponent,
    ProfileEditComponent,
    CreditPageComponent,
    InvestmentDetailsComponent,
    InvestmentCreateComponent,
    CreditDetailsComponent
  ],
  exports: [
    ConversationDetailsComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    MatCardModule,
    FormsModule,
    FontAwesomeModule,
    MatExpansionModule,
    ReactiveFormsModule,
    MomentModule,
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
    MatDialogModule,
    MatTabsModule,
    MatChipsModule,
    MatRadioModule,
    MatProgressSpinnerModule,
    MatToolbarModule,
    MatCheckboxModule,
    MatSlideToggleModule,
    Ng5SliderModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatProgressBarModule
  ],
  providers: [BankAccountService, CreditService],
  entryComponents: [DialogWindowComponent, InvestmentDetailsComponent],
})
export class MainModule { }
