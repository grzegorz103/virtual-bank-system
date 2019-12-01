package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InstallmentIn {
    @Positive
    private Long sourceSaldoId;

    @NotEmpty
    @NotNull
    private String currency;

    @Positive
    private Long creditId;
}
