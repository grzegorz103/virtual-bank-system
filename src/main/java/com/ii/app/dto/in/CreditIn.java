package com.ii.app.dto.in;

import com.ii.app.models.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditIn
{
        private Long destinedSaldoId;

        private BigDecimal totalBalance;

        private Integer totalInstallmentCount;

        private Currency currency;

        private BigDecimal installmentAmount;
}
