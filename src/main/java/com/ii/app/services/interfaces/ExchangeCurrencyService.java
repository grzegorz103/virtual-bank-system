package com.ii.app.services.interfaces;

import com.ii.app.dto.in.ExchangeCurrencyIn;
import com.ii.app.dto.out.ExchangeCurrencyOut;

import java.math.BigDecimal;

public interface ExchangeCurrencyService
{
        ExchangeCurrencyOut create ( ExchangeCurrencyIn exchangeCurrencyIn );

        BigDecimal calculate(ExchangeCurrencyIn exchangeCurrencyIn);
}
