package com.ii.app.services;

import com.ii.app.dto.out.BankAccTypeOut;
import com.ii.app.mappers.BankAccTypeMapper;
import com.ii.app.mappers.BankAccountMapper;
import com.ii.app.models.BankAccType;
import com.ii.app.repositories.BankAccountTypeRepository;
import com.ii.app.services.interfaces.BankAccTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccTypeServiceImpl implements BankAccTypeService
{
        private final BankAccountTypeRepository bankAccountTypeRepository;

        private final BankAccTypeMapper bankAccTypeMapper;

        @Autowired
        public BankAccTypeServiceImpl ( BankAccountTypeRepository bankAccountTypeRepository,
                                        BankAccTypeMapper bankAccTypeMapper )
        {
                this.bankAccountTypeRepository = bankAccountTypeRepository;
                this.bankAccTypeMapper = bankAccTypeMapper;
        }

        @Override
        public List<BankAccTypeOut> findAll ()
        {
                return bankAccountTypeRepository.findAll()
                        .stream()
                        .map( bankAccTypeMapper::entityToDto )
                        .collect( Collectors.toList() );
        }
}
