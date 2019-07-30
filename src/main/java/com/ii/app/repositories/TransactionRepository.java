package com.ii.app.repositories;

import com.ii.app.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
}
