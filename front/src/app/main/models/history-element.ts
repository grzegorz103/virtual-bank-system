import { BankAccount } from './bank-account';
import { TransactionHistory } from './transaction-history';

export class TransactionIn implements TransactionHistory {
    id: number;
    date: Date;
    balance: number;
    balanceWithCommission: number;
    sourceBankAccount: BankAccount;
    title: string;
    destinedBankAccount: BankAccount;
    type: string;
    sourceCurrency: any;
    destinedCurrency: any;

    constructor(transaction?: TransactionIn, type?) {
        this.id = transaction.id;
        this.sourceBankAccount = transaction.sourceBankAccount;
        this.sourceCurrency = transaction.sourceCurrency;
        this.destinedBankAccount = transaction.destinedBankAccount;
        this.balance = transaction.balance;
        this.destinedCurrency = transaction.destinedCurrency;
        this.title = transaction.title;
        this.date = transaction.date;
        this.type = type;
    }
}