import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MainRoutingModule } from './main-routing.module';
import { MainComponent } from './main.component';
import { BankAccountListComponent } from './views/bank-account-list/bank-account-list.component';
import { TransactionComponent } from './views/transaction/transaction.component';
import { TransactionMultiCurrencyComponent } from './views/transaction-multi-currency/transaction-multi-currency.component';
import { NavbarComponent } from '../shared/navbar/navbar.component';
import { ExchangeCurrencyComponent } from './views/exchange-currency/exchange-currency.component';
import { BankAccountDetailsComponent } from './views/bank-account-details/bank-account-details.component';
import { TemplateListComponent } from './views/transaction-templates/template-list/template-list.component';
import { TemplateCreateComponent } from './views/transaction-templates/template-create/template-create.component';
import { TemplateDetailsComponent } from '../template-details/template-details.component';
import { DialogWindowComponent } from './misc/dialog-window/dialog-window.component';
import { CreditCreateComponent } from './views/credit-create/credit-create.component';
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
import { ConversationDetailsComponent } from './conversation-details/conversation-details.component';
import { UserSupportComponent } from './user-support/user-support.component';
import { MomentModule } from 'angular2-moment';
import { ProfileEditComponent } from './profile-edit/profile-edit.component';
import { ExchangePageComponent } from './views/exchange-page/exchange-page.component';
import { CreditPageComponent } from './views/credit-page/credit-page.component';
import { CreditDetailsComponent } from './views/credit-details/credit-details.component';
import { InvestmentListComponent } from './views/investment-list/investment-list.component';


@NgModule({
  declarations: [MainComponent,
    BankAccountListComponent,
    TransactionComponent,
    TransactionMultiCurrencyComponent,
    ExchangeCurrencyComponent,
    BankAccountDetailsComponent,
    TemplateListComponent,
    TemplateCreateComponent,
    TemplateDetailsComponent,
    DialogWindowComponent,
    CreditCreateComponent,
    ConversationDetailsComponent,
    UserSupportComponent,
    ExchangePageComponent,
    InvestmentListComponent,
    ProfileEditComponent,
    CreditPageComponent,
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
  entryComponents: [DialogWindowComponent],
})
export class MainModule { }
