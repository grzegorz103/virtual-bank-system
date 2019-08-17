package com.ii.app.services.interfaces;

import com.ii.app.dto.in.BankAccountIn;
import com.ii.app.dto.out.BankAccountOut;

import java.util.List;

public interface BankAccountService
{
        BankAccountOut create( BankAccountIn bankAccountIn);

        List<BankAccountOut> findAll();

        List<BankAccountOut> findByUser();
}
