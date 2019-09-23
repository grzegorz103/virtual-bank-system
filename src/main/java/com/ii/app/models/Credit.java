package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table (name = "credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credit
{
        @Id
        @GeneratedValue (strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne
        @JoinColumn (name = "saldo_id")
        private Saldo destinedSaldo;

        @Column (name = "total_balance")
        private BigDecimal totalBalance;

        @Column (name = "balance_paid")
        private BigDecimal balancePaid;

        // wysokość raty
        @Column (name = "installment_amount")
        private BigDecimal installmentAmount;

        // ilość rat
        @Column (name = "total_installment_count")
        private Integer totalInstallmentCount;

        @OneToMany (mappedBy = "credit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JsonIgnore
        private Set<Installment> installments;
}
