package com.ii.app.dto;

import com.ii.app.models.BankAccount;
import com.ii.app.utils.validators.BankAccountExists;
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
public class TransactionDTO {
    private String sourceAccountNumber;
    private String sourceCurrency;

    private String destinedAccountNumber;
    private String destinedCurrency;
    private float balance;
    private String title;
}
