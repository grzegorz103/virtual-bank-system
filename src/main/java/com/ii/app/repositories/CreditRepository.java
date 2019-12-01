package com.ii.app.repositories;

import com.ii.app.models.Credit;
import com.ii.app.models.enums.CreditStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findAllByDestinedSaldo_BankAccount_User_Identifier(String identifier);

    List<Credit> findAllByCreditStatus_CreditType(CreditStatus.CreditType creditType);

    List<Credit> findAllByCreditStatusAndDestinedSaldo_BankAccount_Id(CreditStatus creditStatus, Long id);

    Long countAllByCreditStatus_CreditType(CreditStatus.CreditType creditType);
}
