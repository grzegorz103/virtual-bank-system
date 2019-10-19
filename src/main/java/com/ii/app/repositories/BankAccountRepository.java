package com.ii.app.repositories;

import com.ii.app.models.BankAccType;
import com.ii.app.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByNumber(String number);

    List<BankAccount> findByUserIdentifier(String identifier);

    Long countByBankAccType(BankAccType bankAccType);

    boolean existsByNumber(String number);
}
