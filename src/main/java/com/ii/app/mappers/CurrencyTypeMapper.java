package com.ii.app.mappers;

import com.ii.app.dto.in.CurrencyTypeIn;
import com.ii.app.dto.out.CurrencyTypeOut;
import com.ii.app.models.CurrencyType;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface CurrencyTypeMapper
{
        CurrencyType DTOtoEntity ( CurrencyTypeIn currencyTypeIn );

        CurrencyTypeOut entityToDTO ( CurrencyType currencyType );
}