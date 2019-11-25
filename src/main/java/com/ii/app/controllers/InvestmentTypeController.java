package com.ii.app.controllers;

import com.ii.app.dto.out.InvestmentTypeOut;
import com.ii.app.models.enums.InvestmentType;
import com.ii.app.services.interfaces.InvestmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/investmentTypes")
public class InvestmentTypeController {

    private final InvestmentTypeService investmentTypeService;

    @Autowired
    public InvestmentTypeController(InvestmentTypeService investmentTypeService) {
        this.investmentTypeService = investmentTypeService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<InvestmentTypeOut> findAll() {
        return investmentTypeService.findAll();
    }
}
