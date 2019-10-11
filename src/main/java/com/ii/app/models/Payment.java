package com.ii.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

// konto
// waluta
// ilosc waluty
// wplata
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destined_bank_account_id")
    private BankAccount destinedBankAccount;

    @ManyToOne
    @JoinColumn(name = "source_currency_type_id")
    private CurrencyType sourceCurrencyType;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name="date")
    private Instant date;
}
