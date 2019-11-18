import { AbstractControl } from '@angular/forms';

export class BankAccountNumberValidator {

    public static validate(control: AbstractControl) {
        let value = String(control.value);

        if (value) {
            value = value.replace(/\s/g, "");
            if (value.length === 26 && value.match(/\d{26}/)) {
                return null;
            }
        }
        return { accountNumber: true };
    }

}