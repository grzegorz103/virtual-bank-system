package com.ii.app.dto.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoEdit {

    @Min(0)
    private BigDecimal balance;

}
