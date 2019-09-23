package com.ii.app.controllers;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.services.interfaces.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/credits")
public class CreditController
{
        private final CreditService creditService;

        @Autowired
        public CreditController ( CreditService creditService )
        {
                this.creditService = creditService;
        }

        @PostMapping
        public CreditOut create ( @RequestBody CreditIn creditIn )
        {
                return creditService.create( creditIn );
        }

}
