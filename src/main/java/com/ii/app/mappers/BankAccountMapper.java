package com.ii.app.mappers;

import com.ii.app.dto.in.BankAccountIn;
import com.ii.app.dto.out.BankAccountOut;
import com.ii.app.models.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper
{

        BankAccountOut entityToDTO ( BankAccount bankAccount );
}
