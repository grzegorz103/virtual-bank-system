package com.ii.app.services;

import com.ii.app.dto.in.InstallmentIn;
import com.ii.app.dto.out.InstallmentOut;
import com.ii.app.mappers.InstallmentMapper;
import com.ii.app.models.Credit;
import com.ii.app.models.Installment;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.CreditStatus;
import com.ii.app.repositories.CreditRepository;
import com.ii.app.repositories.CreditStatusRepository;
import com.ii.app.repositories.InstallmentRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Service
public class InstallmentServiceImpl implements InstallmentService
{
        private final InstallmentRepository installmentRepository;

        private final InstallmentMapper installmentMapper;

        private final CreditRepository creditRepository;

        private final SaldoRepository saldoRepository;

        private final CreditStatusRepository creditStatusRepository;

        @Autowired
        public InstallmentServiceImpl ( InstallmentMapper installmentMapper,
                                        InstallmentRepository installmentRepository,
                                        CreditRepository creditRepository,
                                        SaldoRepository saldoRepository,
                                        CreditStatusRepository creditStatusRepository )
        {
                this.installmentMapper = installmentMapper;
                this.installmentRepository = installmentRepository;
                this.creditRepository = creditRepository;
                this.saldoRepository = saldoRepository;
                this.creditStatusRepository = creditStatusRepository;
        }

        @Override
        public InstallmentOut create ( InstallmentIn installmentIn )
        {
                Installment mapped = installmentMapper.dtoToEntity( installmentIn );
                Credit credit = creditRepository.findById( installmentIn.getCreditId() ).orElseThrow( () -> new RuntimeException( "Not found" ) );
                Saldo sourceSaldo = saldoRepository.findById( installmentIn.getSourceSaldoId() ).orElseThrow( () -> new RuntimeException( "Not found" ) );

                if ( !Objects.equals( sourceSaldo.getCurrencyType().getCurrency().name(), installmentIn.getCurrency().name() ) )
                {
                        throw new RuntimeException( "Invalid source saldo currency type" );
                }

                // wysokosc raty
                BigDecimal installmentAmount = credit.getInstallmentAmount();
                if ( sourceSaldo.getBalance().compareTo( installmentAmount ) < 0 )
                {
                        throw new RuntimeException( "Insufficient balance" );
                }

                sourceSaldo.setBalance( sourceSaldo.getBalance().subtract( installmentAmount ) );
                credit.setTotalInstallmentCount( credit.getTotalInstallmentCount() + 1 );
                credit.setBalancePaid( credit.getBalancePaid().add( installmentAmount ) );
                mapped.setPayDate( Instant.now() );
                mapped.setCredit( credit );
                mapped.setAmount( installmentAmount );

                if ( credit.getBalancePaid().compareTo( credit.getTotalBalance() ) >= 0 )
                {
                        credit.setCreditStatus( creditStatusRepository.findByCreditType( CreditStatus.CreditType.PAID ) );
                }

                return installmentMapper.entityToDto( installmentRepository.save( mapped ) );
        }
}
