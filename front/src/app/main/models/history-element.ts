import { BankAccount } from './bank-account';
import { TransactionHistory } from './transaction-history';
import { CurrencyType } from './currency-type';

export class TransactionIn extends TransactionHistory {
    balanceWithCommission: number;
    title: string;
    type: string;
    destinedCurrencyType: CurrencyType;
    sourceBankAccount: BankAccount;

    constructor(transaction?: TransactionIn, type?) {
        super(transaction.id,
             transaction.date,
             transaction.balance,
             transaction.sourceCurrencyType,
              transaction.destinedBankAccount,
          'TransactionIn');
        this.title = transaction.title;
        this.balanceWithCommission = transaction.balanceWithCommission;
        this.type = type;
        this.sourceBankAccount = transaction.sourceBankAccount;
        this.destinedCurrencyType = transaction.destinedCurrencyType;
    }

}
