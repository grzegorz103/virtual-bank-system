package com.ii.app.services.interfaces;

import com.ii.app.dto.in.CurrencyTypeIn;
import com.ii.app.dto.out.CurrencyTypeOut;

import java.util.List;

public interface CurrencyTypeService
{
        List<CurrencyTypeOut> findAll();

        CurrencyTypeOut create(CurrencyTypeIn currencyTypeIn);

        CurrencyTypeOut update(CurrencyTypeIn currencyTypeIn);

        void deleteById(Long id);
}
