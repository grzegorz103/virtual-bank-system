package com.ii.app.mappers;

import com.ii.app.dto.in.InvestmentIn;
import com.ii.app.dto.out.InvestmentOut;
import com.ii.app.models.Investment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = InvestmentTypeMapper.class)
public interface InvestmentMapper {
    InvestmentOut entityToDTO(Investment investment);

    Investment dtoToEntity(InvestmentIn investmentIn);

    List<InvestmentOut> entityToDTO(List<Investment> investments);
}
