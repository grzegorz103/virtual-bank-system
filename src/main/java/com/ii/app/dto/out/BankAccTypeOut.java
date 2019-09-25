package com.ii.app.dto.out;

import com.ii.app.models.enums.BankAccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccTypeOut
{
        private Long id;

        private BankAccountType bankAccountType;

        private Float transactionComission;

        private Float exchangeCurrencyCommission;
}
