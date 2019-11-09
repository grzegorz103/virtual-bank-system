package com.ii.app.dto.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankAccTypeEdit {
    private Long id;
    private BigDecimal transactionComission;
    private BigDecimal exchangeCurrencyCommission;
}
