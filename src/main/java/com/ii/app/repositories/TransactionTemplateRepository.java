package com.ii.app.repositories;

import com.ii.app.models.TransactionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTemplateRepository extends JpaRepository<TransactionTemplate, Long>
{
}
