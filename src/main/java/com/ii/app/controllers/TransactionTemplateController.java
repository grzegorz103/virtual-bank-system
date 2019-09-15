package com.ii.app.controllers;

import com.ii.app.dto.in.TransactionTemplateIn;
import com.ii.app.dto.out.TransactionTemplateOut;
import com.ii.app.models.TransactionTemplate;
import com.ii.app.repositories.TransactionTemplateRepository;
import com.ii.app.services.interfaces.TransactionService;
import com.ii.app.services.interfaces.TransactionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/transactiontemplates")
public class TransactionTemplateController
{
        private final TransactionTemplateService transactionTemplateService;

        @Autowired
        public TransactionTemplateController ( TransactionTemplateService transactionTemplateService )
        {
                this.transactionTemplateService = transactionTemplateService;
        }

        @PostMapping
        public TransactionTemplateOut create ( @RequestBody TransactionTemplateIn transactionTemplateIn )
        {
                return transactionTemplateService.create( transactionTemplateIn );
        }

        @GetMapping
        public List<TransactionTemplateOut> findAll ()
        {
                return transactionTemplateService.findAll();
        }

        @GetMapping ("/{id}")
        public TransactionTemplateOut findOneById ( @PathVariable ("id") Long id )
        {
                return transactionTemplateService.findOneById( id );
        }

        @PutMapping ("/{id}")
        public TransactionTemplateOut updateById ( @PathVariable ("id") Long id,
                                                   @RequestBody TransactionTemplateIn transactionTemplateIn )
        {
                return transactionTemplateService.update( id, transactionTemplateIn );
        }

        @DeleteMapping("/{id}")
        public void deleteById(@PathVariable("id") Long id){
                transactionTemplateService.deleteById(id);
        }
}
