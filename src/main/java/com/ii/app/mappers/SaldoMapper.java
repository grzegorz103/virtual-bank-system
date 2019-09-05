package com.ii.app.mappers;

import com.ii.app.dto.out.SaldoOut;
import com.ii.app.models.Saldo;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring", uses=CurrencyTypeMapper.class)
public interface SaldoMapper
{
        SaldoOut saldoToSaldoOut( Saldo saldo);
}
