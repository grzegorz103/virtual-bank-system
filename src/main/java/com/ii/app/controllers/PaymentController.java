package com.ii.app.controllers;

import com.ii.app.dto.in.PaymentIn;
import com.ii.app.dto.out.PaymentOut;
import com.ii.app.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/payments")
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Secured("ROLE_EMPLOYEE")
    public PaymentOut create(@RequestBody @Valid PaymentIn paymentIn) {
        return paymentService.create(paymentIn);
    }

    @GetMapping("/account/{id}")
    @Secured("ROLE_USER")
    public List<PaymentOut> findByAccountId(@PathVariable("id") Long id) {
        return paymentService.findAllByBankAccountId(id);
    }

    @GetMapping
    @Secured("ROLE_EMPLOYEE")
    public List<PaymentOut> findAll() {
        return paymentService.findAll();
    }
}