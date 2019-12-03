package com.ii.app.mappers;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.models.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CreditStatusMapper.class)
public interface CreditMapper {
    Credit DTOtoEntity(CreditIn creditIn);

    @Mapping(source = "credit.destinedSaldo.id", target = "destinedSaldoId")
    CreditOut entityToDTO(Credit credit);
}
