package com.ii.app.utils;

import com.ii.app.models.CurrencyType;

import java.math.BigDecimal;

public interface CurrencyConverter
{
        BigDecimal convertCurrency ( float currency, CurrencyType sourceCurrency, CurrencyType destinedCurrency );
}
