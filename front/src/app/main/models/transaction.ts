import { TransactionHistory } from './transaction-history';

export class Transaction{
    id: string;
    sourceAccountNumber: string;
    sourceCurrency: string;
    destinedAccountNumber: string;
    destinedCurrency: string;
    balance: number;
    title: string;
    date: Date;
}