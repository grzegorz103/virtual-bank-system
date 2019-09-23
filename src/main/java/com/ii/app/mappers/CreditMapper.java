package com.ii.app.mappers;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.models.Credit;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface CreditMapper
{
        Credit DTOtoEntity ( CreditIn creditIn);

        CreditOut entityToDTO ( Credit credit );
}
