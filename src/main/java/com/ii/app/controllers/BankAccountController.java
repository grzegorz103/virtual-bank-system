package com.ii.app.controllers;

import com.ii.app.dto.edit.BankAccountEdit;
import com.ii.app.dto.edit.SaldoEdit;
import com.ii.app.dto.in.BankAccountIn;
import com.ii.app.dto.out.BankAccountOut;
import com.ii.app.dto.out.SaldoOut;
import com.ii.app.services.interfaces.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bankaccount")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    @Secured("ROLE_EMPLOYEE")
    public List<BankAccountOut> findAll() {
        return bankAccountService.findAll();
    }

    @GetMapping("/byUser")
    @Secured("ROLE_USER")
    public List<BankAccountOut> findByUser() {
        return bankAccountService.findByUser();
    }

    @PostMapping
    @Secured("ROLE_USER")
    public BankAccountOut create(@RequestBody BankAccountIn bankAccountIn,
                                 @AuthenticationPrincipal String username) {
        return bankAccountService.create(bankAccountIn, username);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public BankAccountOut findById(@PathVariable("id") Long id) {
        return bankAccountService.findById(id);
    }

    @GetMapping("/{id}/accountCount")
    @Secured("ROLE_ADMIN")
    public Long getBankAccountCountByType(@PathVariable("id") Long id) {
        return bankAccountService.findBankAccountCountByType(id);
    }

    @Secured({"ROLE_USER", "ROLE_EMPLOYEE"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        bankAccountService.deleteById(id);
    }

    @Secured("ROLE_EMPLOYEE")
    @PutMapping("/{id}")
    public BankAccountOut update(@PathVariable("id") Long id,
                                 @RequestBody @Valid BankAccountEdit bankAccountEdit) {
        return bankAccountService.update(id, bankAccountEdit);
    }

    @Secured("ROLE_EMPLOYEE")
    @PutMapping("/saldo/{id}")
    public SaldoOut update(@PathVariable("id") Long id,
                           @RequestBody @Valid SaldoEdit saldoEdit) {
        return bankAccountService.updateSaldo(id, saldoEdit);
    }

}
