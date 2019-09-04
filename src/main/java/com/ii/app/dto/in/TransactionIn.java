package com.ii.app.dto.in;

import com.ii.app.models.BankAccount;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionIn
{
        private BigDecimal balance;

        private BankAccount sourceBankAccount;

        private String title;

        private BankAccount destinedBankAccount;
}
