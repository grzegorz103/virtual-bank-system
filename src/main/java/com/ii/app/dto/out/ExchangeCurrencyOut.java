package com.ii.app.dto.out;

import com.ii.app.models.CurrencyType;
import com.ii.app.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeCurrencyOut
{
        private BigDecimal balance;

        private Currency destCurrency;
}
