package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ii.app.models.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@EqualsAndHashCode(exclude = "exchanges")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", length = 26)
    private String number;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Saldo> saldos;

    @OneToMany(mappedBy = "sourceBankAccount", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "destinedBankAccount", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Payment> payments;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    private Set<ExchangeCurrency> exchanges;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_type_id", nullable = false)
    private BankAccType bankAccType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="removed")
    private boolean removed;
}
