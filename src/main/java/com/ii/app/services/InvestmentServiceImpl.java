package com.ii.app.services;

import com.ii.app.dto.out.InvestmentOut;
import com.ii.app.services.interfaces.InvestmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    @Override
    public List<InvestmentOut> findAll() {
        return null;
    }

    @Override
    public List<InvestmentOut> findAllByUser() {
        return null;
    }

    @Override
    public InvestmentOut findById(Long id) {
        return null;
    }

    @Override
    public Long findBankAccountCountByType(Long id) {
        return null;
    }

    @Override
    public InvestmentOut updateStatus(Long id) {
        return null;
    }
}
