package com.ii.app.controllers;

import com.ii.app.dto.TransactionDTO;
import com.ii.app.models.Transaction;
import com.ii.app.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/transaction")
public class TransactionController
{
        private final TransactionService transactionService;

        @Autowired
        public TransactionController ( TransactionService transactionService )
        {
                this.transactionService = transactionService;
        }

        @PostMapping
        public Transaction create ( @RequestBody TransactionDTO transactionDTO )
        {
                return transactionService.create( transactionDTO );
        }
}
