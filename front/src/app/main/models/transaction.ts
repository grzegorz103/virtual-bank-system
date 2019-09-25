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

}