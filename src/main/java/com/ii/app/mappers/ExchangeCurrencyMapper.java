package com.ii.app.mappers;

import com.ii.app.dto.in.ExchangeCurrencyIn;
import com.ii.app.dto.out.ExchangeCurrencyOut;
import com.ii.app.models.ExchangeCurrency;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface ExchangeCurrencyMapper
{
        ExchangeCurrency DTOtoEntity ( ExchangeCurrencyIn exchangeCurrencyIn );

        ExchangeCurrencyOut entityToDTO ( ExchangeCurrency exchangeCurrency );
}
