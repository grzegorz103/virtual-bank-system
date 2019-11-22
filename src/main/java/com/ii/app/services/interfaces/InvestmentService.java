package com.ii.app.services.interfaces;

import com.ii.app.dto.in.InvestmentIn;
import com.ii.app.dto.out.InvestmentOut;

import java.util.List;

public interface InvestmentService {
    List<InvestmentOut> findAll();

    List<InvestmentOut> findAllByUser();

    InvestmentOut findById(Long id);

    Long findInvestmentCountByType(Long id);

    InvestmentOut updateStatus(Long id);

    InvestmentOut create(InvestmentIn investment);

    List<InvestmentOut> findActiveByBankAccountId(Long bankAccountId);

}