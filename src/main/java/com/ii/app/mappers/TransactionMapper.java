package com.ii.app.mappers;

import com.ii.app.dto.in.TransactionIn;
import com.ii.app.dto.out.TransactionOut;
import com.ii.app.models.Transaction;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface TransactionMapper
{
        Transaction DTOtoEntity( TransactionIn transactionIn);

        TransactionOut entityToDTO( Transaction transaction);
}
