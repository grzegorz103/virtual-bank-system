package com.ii.app.dto.out;

import com.ii.app.models.Saldo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditOut {
    private Long id;

    //      private Saldo destinedSaldo;

    private BigDecimal totalBalance;

    private BigDecimal balancePaid;

    private BigDecimal installmentAmount;

    private Integer totalInstallmentCount;

    private String currency;

    private String destinedSaldoId;

    private CreditStatusOut creditStatus;
//        private Set<Installment> installments;
}
