package com.ii.app.models;

import com.ii.app.models.enums.InvestmentType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "investments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "saldo_id")
    private Saldo destinedSaldo;

    @Column(name = "start_balance")
    private BigDecimal startBalance;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Column(name = "currency_type")
    private String currency;

    @Column(name = "date")
    private Instant creationDate;

    @Column(name="updateTimespan")
    private Instant updateTimespan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "investment_type_id")
    private InvestmentType investmentType;
}