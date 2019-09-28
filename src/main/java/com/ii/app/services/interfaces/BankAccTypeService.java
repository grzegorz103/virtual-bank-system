package com.ii.app.services.interfaces;

import com.ii.app.dto.out.BankAccTypeOut;

import java.util.List;

public interface BankAccTypeService
{
        List<BankAccTypeOut> findAll();
}
