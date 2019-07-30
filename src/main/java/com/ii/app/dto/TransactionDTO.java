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
        private String destinedAccountNumber;
        private String sourceAccountNumber;
        private Currency currency;
        private float balance;
        private String title;
}
