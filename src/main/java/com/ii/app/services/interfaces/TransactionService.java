package com.ii.app.services.interfaces;

import com.ii.app.dto.TransactionDTO;
import com.ii.app.models.Transaction;

public interface TransactionService
{
        Transaction create ( TransactionDTO transactionDTO );
}
