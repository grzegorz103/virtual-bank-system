import { TransactionHistory } from './transaction-history';

export class Transaction implements TransactionHistory {
    id: string;
    sourceAccountNumber: string;
    sourceCurrency: string;
    destinedAccountNumber: string;
    destinedCurrency: string;
    balance: number;
    title: string;
    date: Date;
    type: string;


    constructor(transaction?: Transaction, type?) {
        this.id = transaction.id;
        this.sourceAccountNumber = transaction.sourceAccountNumber;
        this.sourceCurrency = transaction.sourceCurrency;
        this.destinedAccountNumber = transaction.destinedAccountNumber;
        this.balance = transaction.balance;
        this.destinedCurrency = transaction.destinedCurrency;
        this.title = transaction.title;
        this.date = transaction.date;
        this.type = type;
    }
}