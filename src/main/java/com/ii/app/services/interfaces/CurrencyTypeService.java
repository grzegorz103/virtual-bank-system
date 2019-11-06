package com.ii.app.services.interfaces;

import com.ii.app.dto.edit.CurrencyTypeEdit;
import com.ii.app.dto.in.CurrencyTypeIn;
import com.ii.app.dto.out.CurrencyTypeOut;

import java.util.List;

public interface CurrencyTypeService {
    List<CurrencyTypeOut> findAll();

    CurrencyTypeOut create(CurrencyTypeIn currencyTypeIn);

    CurrencyTypeOut update(Long id, CurrencyTypeEdit currencyTypeEdit);

    CurrencyTypeOut findById(Long id);
}
