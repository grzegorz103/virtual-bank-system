package com.ii.app.dto.in;

import com.ii.app.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyTypeIn
{
        private Currency currency;

        private float exchangeRate;
}
