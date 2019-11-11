package com.ii.app.mappers;

import com.ii.app.dto.in.InvestmentIn;
import com.ii.app.dto.out.InvestmentTypeOut;
import com.ii.app.models.enums.InvestmentType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvestmentTypeMapper {
    InvestmentTypeOut entityToDTO(InvestmentType investmentType);

    List<InvestmentTypeOut> entityToDTO(List<InvestmentType> investments);
}
