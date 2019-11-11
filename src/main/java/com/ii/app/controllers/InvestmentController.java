package com.ii.app.controllers;

import com.ii.app.dto.out.InvestmentOut;
import com.ii.app.services.interfaces.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    @Autowired
    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @GetMapping("/byUser")
    @Secured("ROLE_USER")
    public List<InvestmentOut> findAllByUser(){
        return investmentService.findAllByUser();
    }

    @PatchMapping("/{id}")
    public InvestmentOut updateStatus(@PathVariable("id") Long id){
        return investmentService.updateStatus(id);
    }

}
