import { Saldo } from './saldo';
import { BankAccType } from './bank-acc-type'

export class BankAccount {
    id: number;
    number: string;
    saldos: Saldo[];
    bankAccType: BankAccType;
    removed: boolean;
}
