package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditIn
{
        private Long destinedSaldoId;

        private BigDecimal totalBalance;

        private Integer totalInstallmentCount;
}
