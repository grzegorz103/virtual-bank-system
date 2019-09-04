package com.ii.app.dto;

import com.ii.app.models.BankAccount;
import com.ii.app.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.CurrencyType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO
{
        private String sourceAccountNumber;
        private Currency sourceCurrency;
        private String destinedAccountNumber;
        private Currency destinedCurrency;
        private float balance;
        private String title;
}
