import { InvestmentType } from './investment-type';

export class Investment {
    id: number;
    startBalance: number;
    currentBalance: number;
    currency: string;
    creationDate: Date;
    investmentType: InvestmentType;
}
