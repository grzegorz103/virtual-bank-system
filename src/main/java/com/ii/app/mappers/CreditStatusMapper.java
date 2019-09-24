package com.ii.app.mappers;

import com.ii.app.dto.out.CreditStatusOut;
import com.ii.app.models.enums.CreditStatus;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface CreditStatusMapper
{
        CreditStatusOut entityToDTO ( CreditStatus creditStatus );
}
