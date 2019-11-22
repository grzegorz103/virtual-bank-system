package com.ii.app.services.interfaces;

import com.ii.app.dto.in.PaymentIn;
import com.ii.app.dto.out.PaymentOut;

import java.util.List;

public interface PaymentService {
    PaymentOut create(PaymentIn paymentIn);

    List<PaymentOut> findAllByBankAccountId(Long bankAccountId);

    List<PaymentOut> findAll();
}
