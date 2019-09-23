package com.ii.app.controllers;

import com.ii.app.dto.in.InstallmentIn;
import com.ii.app.dto.out.InstallmentOut;
import com.ii.app.services.interfaces.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/installments")
public class InstallmentController
{
        private final InstallmentService installmentService;

        @Autowired
        public InstallmentController ( InstallmentService installmentService )
        {
                this.installmentService = installmentService;
        }

        @PostMapping
        public InstallmentOut create ( @RequestBody InstallmentIn installmentIn )
        {
                return installmentService.create( installmentIn );
        }
}
