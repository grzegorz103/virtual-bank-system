package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "bank_accounts")
@Getter @Setter
@EqualsAndHashCode (exclude="exchanges")
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

        @OneToMany (mappedBy = "bankAccount", fetch = FetchType.EAGER)
        private Set<ExchangeCurrency> exchanges;

        @ManyToOne (fetch = FetchType.EAGER)
        @JoinColumn (name = "bank_account_type_id", nullable = false)
        private BankAccType bankAccType;
}
