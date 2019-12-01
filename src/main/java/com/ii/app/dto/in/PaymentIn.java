package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIn {
    @NotEmpty
    private String destinedBankAccountNumber;

    @NotEmpty
    private String sourceCurrencyType;

    @NotNull
    private BigDecimal balance;

    private Instant date;
}
