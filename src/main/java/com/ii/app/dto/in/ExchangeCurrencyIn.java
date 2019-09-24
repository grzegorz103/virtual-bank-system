package com.ii.app.dto.in;

import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeCurrencyIn
{
        private float balance;

        private String sourceBankAccNumber;

        private String sourceCurrency;

        private String destCurrency;
}
