package com.ii.app.dto.in;

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
        private String sourceCurrency;
        private String destinedAccountNumber;
        private String destinedCurrency;
        private BigDecimal balance;
        private String title;
}
