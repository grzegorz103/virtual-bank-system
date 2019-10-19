package com.ii.app.utils;

import com.ii.app.models.CurrencyType;
import org.hibernate.validator.constraints.Length;
import org.junit.Test;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class CurrencyConverterImplTest {
    @Test
    public void convertCurrencyTest() {
        CurrencyConverterImpl converter = new CurrencyConverterImpl();
        CurrencyType source = new CurrencyType(1L, "PLN", 3.5f, null);
        CurrencyType dest = new CurrencyType(1L, "PLN", 1f, null);

        float res = converter.convertCurrency(10, source, dest).floatValue();

        assertThat(res).isEqualTo(35);
    }

    @Test
    public void convertSignedCurrencyTest() {
        CurrencyConverterImpl converter = new CurrencyConverterImpl();
        CurrencyType source = new CurrencyType(1L, "CHF", 3.5f, null);
        CurrencyType dest = new CurrencyType(1L, "CHF", 1f, null);

        float res = converter.convertCurrency(-5, source, dest).floatValue();

        assertThat(res).isEqualTo(0);
    }

    @Test
    public void roundHalfDownToUpperNumberTest() {
        assertThat(BigDecimal.valueOf(2.121111).setScale(2, RoundingMode.UP))
            .isEqualTo(BigDecimal.valueOf(2.13));

        assertThat(BigDecimal.valueOf(3.910001).setScale(2, RoundingMode.UP))
            .isEqualTo(BigDecimal.valueOf(3.92));

        assertThat(BigDecimal.valueOf(102.011111).setScale(2, RoundingMode.UP))
            .isEqualTo(BigDecimal.valueOf(102.02));
    }

    @Test
    public void roundHalfUpToUpperNumberTest() {
        assertThat(BigDecimal.valueOf(102.017777).setScale(2, RoundingMode.UP))
            .isEqualTo(BigDecimal.valueOf(102.02));
        assertThat(BigDecimal.valueOf(2.329999).setScale(2, RoundingMode.UP))
            .isEqualTo(BigDecimal.valueOf(2.33));
        assertThat(BigDecimal.valueOf(12102.989999).setScale(2, RoundingMode.UP))
            .isEqualTo(BigDecimal.valueOf(12102.99));
    }

}