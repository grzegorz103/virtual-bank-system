package com.ii.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table (name = "installments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Installment
{
        @Id
        @GeneratedValue (strategy = GenerationType.AUTO)
        private Long id;

        @Column (name = "pay_date")
        private Instant payDate;

        @Column (name = "amount")
        private BigDecimal amount;

        @ManyToOne
        @JoinColumn (name = "credit_id")
        private Credit credit;
}
