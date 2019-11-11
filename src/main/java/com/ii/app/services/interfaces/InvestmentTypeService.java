package com.ii.app.services.interfaces;

import com.ii.app.dto.out.InvestmentTypeOut;

import java.util.List;

public interface InvestmentTypeService {
    List<InvestmentTypeOut> findAll();
}
