package com.ii.app.dto.in;

import com.ii.app.models.Saldo;
import com.ii.app.models.enums.BankAccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankAccountIn
{
        private BankAccountType bankAccountType;
}
