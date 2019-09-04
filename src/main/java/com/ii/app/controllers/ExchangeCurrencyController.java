package com.ii.app.controllers;

import com.ii.app.dto.in.ExchangeCurrencyIn;
import com.ii.app.dto.out.ExchangeCurrencyOut;
import com.ii.app.models.ExchangeCurrency;
import com.ii.app.services.interfaces.ExchangeCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/exchangecurrency")
public class ExchangeCurrencyController
{
        private final ExchangeCurrencyService exchangeCurrencyService;

        @Autowired
        public ExchangeCurrencyController ( ExchangeCurrencyService exchangeCurrencyService )
        {
                this.exchangeCurrencyService = exchangeCurrencyService;
        }

        @PostMapping
        public ExchangeCurrencyOut create ( @RequestBody ExchangeCurrencyIn exchangeCurrencyIn )
        {
                return exchangeCurrencyService.create( exchangeCurrencyIn );
        }
}
