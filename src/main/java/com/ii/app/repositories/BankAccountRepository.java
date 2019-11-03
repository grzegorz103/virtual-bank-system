package com.ii.app.repositories;

import com.ii.app.models.BankAccType;
import com.ii.app.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByNumberAndRemovedFalse(String number);

    List<BankAccount> findAllByRemovedFalse();

    List<BankAccount> findByUserIdentifierAndRemovedFalse(String identifier);

    Long countByBankAccTypeAndRemovedFalse(BankAccType bankAccType);

    boolean existsByNumber(String number);

    @Modifying
    @Query("UPDATE BankAccount bankAccount " +
        "SET bankAccount.removed = true " +
        "WHERE bankAccount.id = :bankAccountId")
    void markRemovedAsTrue(@Param("bankAccountId") Long bankAccountId);
}
