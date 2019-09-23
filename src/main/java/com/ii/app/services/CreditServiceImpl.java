package com.ii.app.services;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.mappers.CreditMapper;
import com.ii.app.models.Credit;
import com.ii.app.repositories.CreditRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;

@Service
public class CreditServiceImpl implements CreditService
{
        private final CreditRepository creditRepository;

        private final SaldoRepository saldoRepository;

        private final CreditMapper creditMapper;

        @Autowired
        public CreditServiceImpl ( CreditRepository creditRepository,
                                   CreditMapper creditMapper,
                                   SaldoRepository saldoRepository )
        {
                this.creditRepository = creditRepository;
                this.creditMapper = creditMapper;
                this.saldoRepository = saldoRepository;
        }

        @Override
        public CreditOut create ( CreditIn creditIn )
        {
                Credit mapped = creditMapper.DTOtoEntity( creditIn );
                mapped.setBalancePaid( BigDecimal.ZERO );
                mapped.setDestinedSaldo( saldoRepository.findById( creditIn.getDestinedSaldoId() ).orElseThrow( () -> new RuntimeException( "Not found" ) ) );

                mapped.setInstallments( new HashSet<>() );
                mapped.setInstallmentAmount( BigDecimal.ZERO );

                return creditMapper.entityToDTO( creditRepository.save( mapped ) );
        }
}
