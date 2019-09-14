package com.ii.app.mappers;

import com.ii.app.dto.in.TransactionTemplateIn;
import com.ii.app.dto.out.TransactionTemplateOut;
import com.ii.app.models.TransactionTemplate;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface TransactionTemplateMapper
{
        TransactionTemplateOut entityToDTO ( TransactionTemplate transactionTemplate );

        TransactionTemplate DTOtoEntity ( TransactionTemplateIn transactionTemplateIn );
}
