package com.ii.app.controllers;

import com.ii.app.dto.in.TransactionIn;
import com.ii.app.dto.out.TransactionOut;
import com.ii.app.models.Transaction;
import com.ii.app.services.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Secured("ROLE_USER")
    public TransactionOut create(@Valid @RequestBody TransactionIn transactionIn) {
        return transactionService.create(transactionIn);
    }

    @GetMapping
    public List<TransactionOut> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("/byAccount/{id}")
    public List<TransactionOut> findAllByBankAccountId(@PathVariable("id") Long id) {
        return transactionService.findAllByBankAccountId(id);
    }
}
