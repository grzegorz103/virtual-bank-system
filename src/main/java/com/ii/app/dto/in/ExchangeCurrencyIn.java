package com.ii.app.dto.in;

import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import com.ii.app.models.enums.Currency;
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

        private Currency sourceCurrency;

        private Currency destCurrency;
}
