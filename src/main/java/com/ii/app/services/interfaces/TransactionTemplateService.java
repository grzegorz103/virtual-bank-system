package com.ii.app.services.interfaces;

import com.ii.app.dto.in.TransactionTemplateIn;
import com.ii.app.dto.out.TransactionTemplateOut;

public interface TransactionTemplateService
{
        TransactionTemplateOut create( TransactionTemplateIn transactionTemplateIn);
}
