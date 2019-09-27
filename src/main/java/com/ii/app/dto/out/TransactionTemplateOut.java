package com.ii.app.dto.out;

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

        private String sourceCurrency;

        private String destinedAccountNumber;

        private String destinedCurrency;

        private BigDecimal balance;

        private String title;

        private Instant createDate;

        private Instant modificationDate;
        
        private Boolean multiCurrency;

        private String templateName;
}
