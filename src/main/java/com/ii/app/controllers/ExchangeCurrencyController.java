package com.ii.app.controllers;

import com.ii.app.dto.in.ExchangeCurrencyIn;
import com.ii.app.dto.out.ExchangeCurrencyOut;
import com.ii.app.models.ExchangeCurrency;
import com.ii.app.services.interfaces.ExchangeCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/exchangecurrency")
public class ExchangeCurrencyController {
    private final ExchangeCurrencyService exchangeCurrencyService;

    @Autowired
    public ExchangeCurrencyController(ExchangeCurrencyService exchangeCurrencyService) {
        this.exchangeCurrencyService = exchangeCurrencyService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ExchangeCurrencyOut create(@RequestBody ExchangeCurrencyIn exchangeCurrencyIn) {
        return exchangeCurrencyService.create(exchangeCurrencyIn);
    }

    @PostMapping("/calculate")
    @PreAuthorize("isAuthenticated()")
    public BigDecimal calculate(@RequestBody ExchangeCurrencyIn exchangeCurrencyIn) {
        return exchangeCurrencyService.calculate(exchangeCurrencyIn);
    }
}
