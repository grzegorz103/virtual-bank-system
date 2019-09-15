package com.ii.app.dto.in;

import com.ii.app.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionTemplateIn
{
        private String sourceAccountNumber;
        private Currency sourceCurrency;
        private String destinedAccountNumber;
        private Currency destinedCurrency;
        private BigDecimal balance;
        private String title;
}
