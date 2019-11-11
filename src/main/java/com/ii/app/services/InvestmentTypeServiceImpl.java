package com.ii.app.services;

import com.ii.app.dto.out.InvestmentTypeOut;
import com.ii.app.services.interfaces.InvestmentTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentTypeServiceImpl implements InvestmentTypeService {

    @Override
    public List<InvestmentTypeOut> findAll() {
        return null;
    }

    @Override
    public InvestmentTypeOut update(Long id) {
        return null;
    }
}
