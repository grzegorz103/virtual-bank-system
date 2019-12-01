package com.ii.app.controllers;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.dto.out.CreditStatusOut;
import com.ii.app.models.enums.CreditStatus;
import com.ii.app.services.interfaces.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/credits")
public class CreditController {
    private final CreditService creditService;

    @Autowired
    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping
    @Secured("ROLE_USER")
    public CreditOut create(@RequestBody CreditIn creditIn) {
        return creditService.create(creditIn);
    }

    @GetMapping
    @Secured("ROLE_USER")
    public List<CreditOut> findAllByCurrentUser() {
        return creditService.findByUser();
    }

    @GetMapping("/byType")
    @Secured("ROLE_EMPLOYEE")
    public List<CreditOut> findAllByCreditType(@RequestParam("creditType") CreditStatus.CreditType creditType) {
        return creditService.findByCreditType(creditType);
    }

    @GetMapping("/byId/{id}")
    @Secured({"ROLE_EMPLOYEE", "ROLE_USER"})
    public CreditOut findById(@PathVariable("id") Long id) {
        return creditService.findById(id);
    }

    @PatchMapping("/{id}/status")
    @Secured("ROLE_EMPLOYEE")
    public CreditOut changeStatus(@PathVariable Long id,
                                  @RequestParam("status") Optional<CreditStatus.CreditType> status) {
        if (status.isPresent()) {
            return creditService.changeStatus(id, status.get());
        } else {
            return creditService.changeStatus(id);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/statuses")
    public List<CreditStatusOut> findAllCreditStatuses() {
        return creditService.findAllCreditStatuses();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/count")
    public Long countByCreditType(@RequestParam("type") CreditStatus.CreditType creditType) {
        return creditService.countAllByCreditType(creditType);
    }

    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/byBankAccount/{id}")
    public List<CreditOut> findAllActiveByBankAccountId(@PathVariable("id") Long id) {
        return creditService.findActiveByBankAccountId(id);
    }

}
