package com.ii.app.dto.out;

import com.ii.app.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionTemplateOut
{
        private Long id;

        private String sourceAccountNumber;

        private Currency sourceCurrency;

        private String destinedAccountNumber;

        private Currency destinedCurrency;

        private BigDecimal balance;

        private String title;

        private Instant createDate;

        private Instant modificationDate;
}
