package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ii.app.models.enums.CreditStatus;
import com.ii.app.models.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "credits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "saldo_id")
    private Saldo destinedSaldo;

    @Column(name = "currency_type")
    private String currency;

    @Column(name = "total_balance")
    private BigDecimal totalBalance;

    @Column(name = "balance_paid")
    private BigDecimal balancePaid;

    // wysokość raty
    @Column(name = "installment_amount")
    private BigDecimal installmentAmount;

    // ilość rat
    @Column(name = "total_installment_count")
    private Integer totalInstallmentCount;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Installment> installments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private CreditStatus creditStatus;

    @Column(name="accepted_already")
    private boolean acceptedAlready;
}
