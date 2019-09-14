package com.ii.app.services.interfaces;

import com.ii.app.dto.in.TransactionTemplateIn;
import com.ii.app.dto.out.TransactionTemplateOut;

import java.util.List;

public interface TransactionTemplateService
{
        TransactionTemplateOut create( TransactionTemplateIn transactionTemplateIn);

        List<TransactionTemplateOut> findAll ();
}
