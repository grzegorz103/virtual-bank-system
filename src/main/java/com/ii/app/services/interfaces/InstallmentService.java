package com.ii.app.services.interfaces;

import com.ii.app.dto.in.InstallmentIn;
import com.ii.app.dto.out.InstallmentOut;

public interface InstallmentService
{
        InstallmentOut create ( InstallmentIn installmentIn );
}
