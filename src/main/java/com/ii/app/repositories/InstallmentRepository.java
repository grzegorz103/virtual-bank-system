package com.ii.app.repositories;

import com.ii.app.models.Installment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallmentRepository extends JpaRepository<Installment, Long>
{
}
