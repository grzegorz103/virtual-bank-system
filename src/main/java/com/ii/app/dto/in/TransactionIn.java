package com.ii.app.dto.in;

import com.ii.app.models.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionIn
{
        private BigDecimal balance;

        private BankAccount sourceBankAccount;

        private String title;

        private BankAccount destinedBankAccount;
}
