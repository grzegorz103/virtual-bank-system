import {CurrencyType} from './currency-type';
import {BankAccount} from './bank-account';

export abstract class TransactionHistory {
  id: number;
  date: Date;
  balance: number;
  sourceCurrencyType: CurrencyType;
  destinedBankAccount: BankAccount;
  typeName: string;

  constructor(id, date, balance, sourceCurrencyType, destinedBankAccount, typeName) {
    this.id = id;
    this.date = date;
    this.balance = balance;
    this.sourceCurrencyType = sourceCurrencyType;
    this.destinedBankAccount = destinedBankAccount;
    this.typeName = typeName;
  }
}
