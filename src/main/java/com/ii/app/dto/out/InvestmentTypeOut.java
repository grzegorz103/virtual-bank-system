package com.ii.app.dto.out;

import com.ii.app.models.enums.InvestmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentTypeOut {
    private Long id;
    private InvestmentType.InvestmentStatus investmentStatus;
}
