import { Saldo } from './saldo';

export class Credit {
    id: number;
    destinedSaldo: Saldo;
    currency: string;
    creditStatus: any;
    balancePaid: any;
    totalBalance: any;
    installmentAmount:any;
    destinedSaldoId: string;
}
