import { Address } from './address';

export class User {
    id: number;
    username: string;
    email: string;
    locked: boolean;
    address: Address;
}
