package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyTypeIn {
    @NotEmpty
    @Length(min = 1, max = 255)
    private String currency;

    @Positive
    private BigDecimal exchangeRate;
}
