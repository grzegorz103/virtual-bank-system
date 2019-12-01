package com.ii.app.repositories;

import com.ii.app.models.enums.CreditStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditStatusRepository extends JpaRepository<CreditStatus, Long> {
    CreditStatus findByCreditType(CreditStatus.CreditType creditType);

}
