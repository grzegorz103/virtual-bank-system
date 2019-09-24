package com.ii.app.dto.in;

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

        private String currency;

        private Long creditId;
}
