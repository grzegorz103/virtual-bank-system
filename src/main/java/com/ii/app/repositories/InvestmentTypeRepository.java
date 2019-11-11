package com.ii.app.repositories;

import com.ii.app.models.enums.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentTypeRepository extends JpaRepository<InvestmentType, Long> {
    InvestmentType findByInvestmentStatus(InvestmentType.InvestmentStatus investmentStatus);
}
