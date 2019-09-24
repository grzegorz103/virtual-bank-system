package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeCurrency
{
        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne (fetch = FetchType.LAZY)
        @JsonIgnore
        @JoinColumn (name = "bank_account_id", nullable = false)
        private BankAccount bankAccount;

        @Column (name = "source_currency")
        private String sourceCurrency;

        @Column (name = "destined_currency")
        private String destCurrency;

        @Column (name = "balance")
        private float balance;

        @Column (name = "balance_after_exchange")
        private BigDecimal balanceAfterExchange;

        @Column (name = "date")
        private Instant date;
}
