package com.ii.app.repositories;

import com.ii.app.models.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long>
{
}
