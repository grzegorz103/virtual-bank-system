package com.ii.app.services;

import com.ii.app.dto.out.InvestmentTypeOut;
import com.ii.app.mappers.InvestmentTypeMapper;
import com.ii.app.repositories.InvestmentTypeRepository;
import com.ii.app.services.interfaces.InvestmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentTypeServiceImpl implements InvestmentTypeService {

    private final InvestmentTypeRepository investmentTypeRepository;

    private final InvestmentTypeMapper investmentTypeMapper;

    @Autowired
    public InvestmentTypeServiceImpl(InvestmentTypeRepository investmentTypeRepository,
                                     InvestmentTypeMapper investmentTypeMapper) {
        this.investmentTypeRepository = investmentTypeRepository;
        this.investmentTypeMapper = investmentTypeMapper;
    }

    @Override
    public List<InvestmentTypeOut> findAll() {
        return investmentTypeMapper.entityToDTO(investmentTypeRepository.findAll());
    }

}
