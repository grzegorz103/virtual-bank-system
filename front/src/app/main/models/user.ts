import { Address } from './address';

export class User {
    id: string;
    identifier: string;
    email: string;
    locked: boolean;
    address: Address;
}
