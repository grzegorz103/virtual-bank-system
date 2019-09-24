package com.ii.app.dto.out;

import com.ii.app.models.CurrencyType;
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

        private String destCurrency;
}
