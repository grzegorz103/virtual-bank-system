package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditIn {

    @Positive
    private Long destinedSaldoId;

    @NotNull
    private BigDecimal totalBalance;

    @Positive
    private Integer totalInstallmentCount;
}
