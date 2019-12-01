package com.ii.app.dto.in;

import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeCurrencyIn {

    @Positive
    private float balance;

    @NotEmpty
    private String sourceBankAccNumber;

    @NotEmpty
    private String sourceCurrency;

    @NotEmpty
    private String destCurrency;
}
