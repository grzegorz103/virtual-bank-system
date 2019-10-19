package com.ii.app.dto.in;

import com.ii.app.models.BankAccount;
import com.ii.app.utils.validators.BankAccountExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionIn {
    private BigDecimal balance;

    @BankAccountExists
    private BankAccount sourceBankAccount;

    private String title;

    @BankAccountExists
    private BankAccount destinedBankAccount;
}
