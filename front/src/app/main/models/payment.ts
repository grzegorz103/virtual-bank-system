import { BankAccount } from './bank-account';

import { CurrencyType } from './currency-type';

export class Payment {
    id: string;

    destinedBankAccount: BankAccount;

    sourceCurrencyType: CurrencyType;

    balance: number;

    date: Date;
}
