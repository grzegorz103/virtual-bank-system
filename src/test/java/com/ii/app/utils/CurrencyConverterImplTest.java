package com.ii.app.utils;

import com.ii.app.models.CurrencyType;
import com.ii.app.models.enums.Currency;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class CurrencyConverterImplTest
{
        @Test
        public void convertCurrencyTest ()
        {
                CurrencyConverterImpl converter = new CurrencyConverterImpl();
                CurrencyType source = new CurrencyType( 1L, Currency.CHF, 3.5f, null );
                CurrencyType dest = new CurrencyType( 1L, Currency.PLN, 1f, null );

                float res = converter.convertCurrency( 10, source, dest ).floatValue();

                assertThat( res ).isEqualTo( 35 );
        }

        @Test
        public void convertSignedCurrencyTest ()
        {
                CurrencyConverterImpl converter = new CurrencyConverterImpl();
                CurrencyType source = new CurrencyType( 1L, Currency.CHF, 3.5f, null );
                CurrencyType dest = new CurrencyType( 1L, Currency.PLN, 1f, null );

                float res = converter.convertCurrency( -5, source, dest ).floatValue();

                assertThat( res ).isEqualTo( 0 );
        }
}