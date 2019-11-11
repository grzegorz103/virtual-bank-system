package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ii.app.models.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "saldos")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Saldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_type_id", nullable = false)
    private CurrencyType currencyType;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "destinedSaldo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Credit> credits;

    @OneToMany(mappedBy = "destinedSaldo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Investment> investments;

    public Saldo(BigDecimal balance, CurrencyType currencyType, BankAccount bankAccount) {
        this.balance = balance;
        this.currencyType = currencyType;
        this.bankAccount = bankAccount;
    }
}
