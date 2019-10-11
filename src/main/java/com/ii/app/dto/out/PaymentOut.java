package com.ii.app.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOut {
    private Long id;

    private BankAccountOut destinedBankAccount;

    private CurrencyTypeOut sourceCurrencyType;

    private BigDecimal balance;

    private Instant date;
}
