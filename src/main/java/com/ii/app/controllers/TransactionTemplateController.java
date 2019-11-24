package com.ii.app.controllers;

import com.ii.app.dto.in.TransactionTemplateIn;
import com.ii.app.dto.out.TransactionTemplateOut;
import com.ii.app.models.TransactionTemplate;
import com.ii.app.repositories.TransactionTemplateRepository;
import com.ii.app.services.interfaces.TransactionService;
import com.ii.app.services.interfaces.TransactionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transactiontemplates")
public class TransactionTemplateController {
    private final TransactionTemplateService transactionTemplateService;

    @Autowired
    public TransactionTemplateController(TransactionTemplateService transactionTemplateService) {
        this.transactionTemplateService = transactionTemplateService;
    }

    @PostMapping
    @Secured("ROLE_USER")
    public TransactionTemplateOut create(@RequestBody @Valid TransactionTemplateIn transactionTemplateIn) {
        return transactionTemplateService.create(transactionTemplateIn);
    }

    @GetMapping
    @Secured("ROLE_USER")
    public List<TransactionTemplateOut> findAll() {
        return transactionTemplateService.findAll();
    }

    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public TransactionTemplateOut findOneById(@PathVariable("id") Long id) {
        return transactionTemplateService.findOneById(id);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public TransactionTemplateOut updateById(@PathVariable("id") Long id,
                                             @RequestBody @Valid TransactionTemplateIn transactionTemplateIn) {
        return transactionTemplateService.update(id, transactionTemplateIn);
    }

    @GetMapping("/users/all")
    @Secured("ROLE_USER")
    public List<TransactionTemplateOut> findAllByCurrentUser() {
        return transactionTemplateService.findAllByCurrentUser();
    }


    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public void deleteById(@PathVariable("id") Long id) {
        transactionTemplateService.deleteById(id);
    }
}
