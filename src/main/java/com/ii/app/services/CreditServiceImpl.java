package com.ii.app.services;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.mappers.CreditMapper;
import com.ii.app.models.Credit;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.CreditStatus;
import com.ii.app.repositories.CreditRepository;
import com.ii.app.repositories.CreditStatusRepository;
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

        private final CreditStatusRepository creditStatusRepository;

        @Autowired
        public CreditServiceImpl ( CreditRepository creditRepository,
                                   CreditMapper creditMapper,
                                   SaldoRepository saldoRepository,
                                   CreditStatusRepository creditStatusRepository )
        {
                this.creditRepository = creditRepository;
                this.creditMapper = creditMapper;
                this.saldoRepository = saldoRepository;
                this.creditStatusRepository = creditStatusRepository;
        }

        @Override
        public CreditOut create ( CreditIn creditIn )
        {
                Credit mapped = creditMapper.DTOtoEntity( creditIn );
                mapped.setBalancePaid( BigDecimal.ZERO );

                Saldo destinedSaldo = saldoRepository.findById( creditIn.getDestinedSaldoId() )
                        .orElseThrow( () -> new RuntimeException( "Not found" ) );

                destinedSaldo.setBalance( destinedSaldo.getBalance().add( creditIn.getTotalBalance() ) );
                mapped.setCurrency( destinedSaldo.getCurrencyType().getCurrency() );
                mapped.setDestinedSaldo( destinedSaldo );
                mapped.setInstallments( new HashSet<>() );
                mapped.setCreditStatus(creditStatusRepository.findByCreditType( CreditStatus.CreditType.AWAITING ));

                BigDecimal installmentAmount = new BigDecimal(
                        creditIn.getTotalBalance().floatValue() / creditIn.getTotalInstallmentCount()
                ).setScale( 2, BigDecimal.ROUND_UP );
                mapped.setInstallmentAmount( installmentAmount );

                return creditMapper.entityToDTO( creditRepository.save( mapped ) );
        }
}
