package com.ii.app.controllers;

import com.ii.app.dto.in.PaymentIn;
import com.ii.app.dto.out.PaymentOut;
import com.ii.app.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/payments")
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    //@Secured("ROLE_EMPLOYEE")
    public PaymentOut create(@RequestBody PaymentIn paymentIn) {
        return paymentService.create(paymentIn);
    }
}
