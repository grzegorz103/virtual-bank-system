package com.ii.app.mappers;

import com.ii.app.dto.in.InstallmentIn;
import com.ii.app.dto.out.InstallmentOut;
import com.ii.app.models.Installment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstallmentMapper {
    Installment dtoToEntity(InstallmentIn installmentIn);

    InstallmentOut entityToDto(Installment installment);
}
