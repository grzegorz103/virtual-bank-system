package com.ii.app.dto.in;

import com.ii.app.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InstallmentIn
{
        // stad zostanie pobrana kwota do wplaty
        private Long sourceSaldoId;

        private Currency currency;

        private Long creditId;
}
