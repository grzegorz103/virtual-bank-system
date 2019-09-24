package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table (name = "currency_types")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CurrencyType
{
        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        private float exchangeRate;

        @OneToMany (mappedBy = "currencyType", fetch = FetchType.EAGER)
        @JsonIgnore
        private Set<Saldo> saldos;

}
