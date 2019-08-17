package com.ii.app.services.interfaces;

import com.ii.app.dto.out.CurrencyTypeOut;

import java.util.List;

public interface CurrencyTypeService
{
        List<CurrencyTypeOut> findAll();
}
