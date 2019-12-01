package com.ii.app.services.interfaces;

import com.ii.app.dto.edit.BankAccountEdit;
import com.ii.app.dto.edit.SaldoEdit;
import com.ii.app.dto.in.BankAccountIn;
import com.ii.app.dto.out.BankAccountOut;
import com.ii.app.dto.out.SaldoOut;

import java.util.List;

public interface BankAccountService {
    BankAccountOut create(BankAccountIn bankAccountIn, String userIdentifier);

    List<BankAccountOut> findAll();

    List<BankAccountOut> findByUser();

    BankAccountOut findById(Long id);

    Long findBankAccountCountByType(Long id);

    void deleteById(Long id);

    BankAccountOut update(Long id, BankAccountEdit bankAccountEdit);

    SaldoOut updateSaldo(Long id, SaldoEdit saldoEdit);

}
