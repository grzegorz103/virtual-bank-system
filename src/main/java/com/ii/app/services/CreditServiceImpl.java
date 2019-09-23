package com.ii.app.services;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.mappers.CreditMapper;
import com.ii.app.models.Credit;
import com.ii.app.models.Saldo;
import com.ii.app.repositories.CreditRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;

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

                Saldo destinedSaldo = saldoRepository.findById( creditIn.getDestinedSaldoId() )
                        .orElseThrow( () -> new RuntimeException( "Not found" ) );
                if ( !Objects.equals(destinedSaldo.getCurrencyType().getCurrency(), creditIn.getCurrency()) )
                        throw new RuntimeException( "Currency type mismatch" );
                destinedSaldo.setBalance( destinedSaldo.getBalance().add( creditIn.getTotalBalance() ) );
                mapped.setDestinedSaldo( destinedSaldo );
                mapped.setInstallments( new HashSet<>() );

                return creditMapper.entityToDTO( creditRepository.save( mapped ) );
        }
}
