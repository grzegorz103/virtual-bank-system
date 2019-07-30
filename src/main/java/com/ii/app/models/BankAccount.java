package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "bank_accounts")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BankAccount
{
        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
        private Long id;

        @Column (name = "number")
        private String number;

        @OneToMany (mappedBy = "bankAccount", cascade = CascadeType.ALL)
        @JsonIgnore
        private Set<Saldo> saldos;

        @OneToMany (mappedBy = "sourceBankAccount", cascade = CascadeType.ALL)
        @JsonIgnore
        private Set<Transaction> transactions;

        @Column (name = "multi_currency")
        private boolean multiCurrency;
}
