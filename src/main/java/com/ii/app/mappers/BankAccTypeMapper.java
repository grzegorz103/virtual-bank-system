package com.ii.app.mappers;

import com.ii.app.dto.out.BankAccTypeOut;
import com.ii.app.models.BankAccType;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface BankAccTypeMapper
{
        BankAccTypeOut entityToDto ( BankAccType bankAccType );
}
