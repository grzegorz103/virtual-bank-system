package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionTemplateIn {
    @NotBlank
    @Length(min = 26, max = 26)
    private String sourceAccountNumber;

    @NotBlank
    private String sourceCurrency;

    @NotBlank
    @Length(min = 26, max = 26)
    private String destinedAccountNumber;

    @NotBlank
    private String destinedCurrency;

    @NotNull
    @Min(1)
    private BigDecimal balance;

    @NotBlank
    @Length(min = 1, max = 100)
    private String title;
    private Boolean multiCurrency;

    @NotBlank
    @Length(min = 1, max = 100)
    private String templateName;
}
