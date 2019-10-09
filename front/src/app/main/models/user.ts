import { Address } from './address';

export class User {
    ID: number;
    username: string;
    email: string;
    locked: boolean;
    address: Address;
}
