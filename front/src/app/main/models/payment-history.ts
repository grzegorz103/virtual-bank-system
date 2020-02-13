import { TransactionHistory } from './transaction-history';
import { Payment } from './payment';

export class PaymentHistory extends TransactionHistory  {

    constructor(payment: Payment){
        super(payment.id, payment.date, payment.balance, payment.sourceCurrencyType, payment.destinedBankAccount, 'PaymentHistory');
    }

}
