package com.ii.app.dto.out;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentOut {
    private Long id;

    private BankAccountOut destinedBankAccount;

    private CurrencyTypeOut sourceCurrencyType;

    private BigDecimal balance;

    private Instant date;
}
