package com.ii.app.mappers;

import com.ii.app.dto.out.BankAccountOut;
import com.ii.app.models.BankAccType;
import com.ii.app.models.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SaldoMapper.class, BankAccTypeMapper.class})
public interface BankAccountMapper {
    BankAccountOut entityToDTO(BankAccount bankAccount);
}
