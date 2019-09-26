import { BankAccount } from './bank-account';
import { TransactionHistory } from './transaction-history';
import { CurrencyType } from './currency-type';

export class TransactionIn implements TransactionHistory {
    id: number;
    date: Date;
    balance: number;
    balanceWithCommission: number;
    sourceBankAccount: BankAccount;
    title: string;
    destinedBankAccount: BankAccount;
    type: string;
    sourceCurrencyType: CurrencyType;
    destinedCurrencyType: CurrencyType;

    constructor(transaction?: TransactionIn, type?) {
        this.id = transaction.id;
        this.sourceBankAccount = transaction.sourceBankAccount;
        this.sourceCurrencyType = transaction.sourceCurrencyType;
        this.destinedBankAccount = transaction.destinedBankAccount;
        this.balance = transaction.balance;
        this.destinedCurrencyType = transaction.destinedCurrencyType;
        this.title = transaction.title;
        this.date = transaction.date;
        this.type = type;
    }
}