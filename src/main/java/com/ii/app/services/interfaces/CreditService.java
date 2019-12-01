package com.ii.app.services.interfaces;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.dto.out.CreditStatusOut;
import com.ii.app.dto.out.InvestmentOut;
import com.ii.app.models.Credit;
import com.ii.app.models.enums.CreditStatus;

import java.util.List;

public interface CreditService {
    CreditOut create(CreditIn creditIn);

    CreditOut changeStatus(Long creditId);

    CreditOut changeStatus(Long creditId, CreditStatus.CreditType creditType);

    List<CreditOut> findByUser();

    List<CreditOut> findByCreditType(CreditStatus.CreditType creditType);

    CreditOut findById(Long id);

    List<CreditOut> findActiveByBankAccountId(Long bankAccountId);

    List<CreditStatusOut> findAllCreditStatuses();

    Long countAllByCreditType(CreditStatus.CreditType creditType);
}
