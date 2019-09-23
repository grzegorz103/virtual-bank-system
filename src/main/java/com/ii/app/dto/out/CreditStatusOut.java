package com.ii.app.dto.out;

import com.ii.app.models.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditStatusOut
{
        private Long id;

        private CreditStatus.CreditType creditType;
}
