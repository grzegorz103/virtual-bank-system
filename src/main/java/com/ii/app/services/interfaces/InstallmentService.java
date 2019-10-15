package com.ii.app.services.interfaces;

import com.ii.app.dto.in.InstallmentIn;
import com.ii.app.dto.out.InstallmentOut;

import java.util.List;

public interface InstallmentService {
    InstallmentOut create(InstallmentIn installmentIn);

    List<InstallmentOut> findAllByCreditId(Long id);
}
