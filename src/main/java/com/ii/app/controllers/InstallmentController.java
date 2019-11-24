package com.ii.app.controllers;

import com.ii.app.dto.in.InstallmentIn;
import com.ii.app.dto.out.InstallmentOut;
import com.ii.app.services.interfaces.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/installments")
public class InstallmentController {
    private final InstallmentService installmentService;

    @Autowired
    public InstallmentController(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @PostMapping
    @Secured("ROLE_USER")
    public InstallmentOut create(@RequestBody InstallmentIn installmentIn) {
        return installmentService.create(installmentIn);
    }

    @GetMapping("/byCredit/{id}")
    @Secured({"ROLE_USER", "ROLE_EMPLOYEE"})
    public List<InstallmentOut> findAllByCreditId(@PathVariable("id") Long id) {
        return installmentService.findAllByCreditId(id);
    }

}
