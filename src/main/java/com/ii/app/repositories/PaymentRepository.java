package com.ii.app.repositories;

import com.ii.app.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByDestinedBankAccount_Id(Long bankAccountId);
}
