import { Address } from './address';

export class User {
    ID: number;
    identifier: string;
    email: string;
    locked: boolean;
    address: Address;
}
