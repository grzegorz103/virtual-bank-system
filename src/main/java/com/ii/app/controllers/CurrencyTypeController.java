package com.ii.app.controllers;

import com.ii.app.dto.out.CurrencyTypeOut;
import com.ii.app.services.interfaces.CurrencyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api/currencytype")
public class CurrencyTypeController
{
        private final CurrencyTypeService currencyTypeService;

        @Autowired
        public CurrencyTypeController ( CurrencyTypeService currencyTypeService )
        {
                this.currencyTypeService = currencyTypeService;
        }

        @GetMapping
        public List<CurrencyTypeOut> findAll ()
        {
                return currencyTypeService.findAll();
        }
}
