package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIn {
    private String destinedBankAccountNumber;

    private String sourceCurrencyType;

    private BigDecimal balance;

    private Instant date;
}
