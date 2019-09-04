package com.ii.app.dto.out;

import com.ii.app.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyTypeOut
{
        private Long id;

        private Currency currency;

        private float exchangeRate;
}
