package com.ii.app.services.interfaces;

import com.ii.app.dto.TransactionDTO;
import com.ii.app.dto.out.TransactionOut;
import com.ii.app.models.Transaction;

import java.util.List;

public interface TransactionService
{
        Transaction create ( TransactionDTO transactionDTO );

        List<TransactionOut> findAll ();

        List<TransactionOut> findAllByBankAccountId(Long bankAccountId);
}