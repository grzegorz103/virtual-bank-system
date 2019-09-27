export class TransactionTemplate {
    id: number;
    sourceAccountNumber: string;
    sourceCurrency: string;
    destinedAccountNumber: string;
    destinedCurrency: string;
    balance: number;
    title: string;
    createDate: Date;
    modificationDate: Date;
    multiCurrency: boolean;
    templateName: string;
}
