package com.ii.app.services.interfaces;

import com.ii.app.dto.in.ExchangeCurrencyIn;
import com.ii.app.dto.out.ExchangeCurrencyOut;

public interface ExchangeCurrencyService
{
        ExchangeCurrencyOut create ( ExchangeCurrencyIn exchangeCurrencyIn );
}
