package com.ii.app.dto.out;

import com.ii.app.models.Saldo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditOut
{
        private Long id;

  //      private Saldo destinedSaldo;

        private BigDecimal totalBalance;

        private BigDecimal balancePaid;

        private BigDecimal installmentAmount;

        private Integer totalInstallmentCount;

//        private Set<Installment> installments;
}
