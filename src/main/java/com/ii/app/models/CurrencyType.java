package com.ii.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ii.app.models.enums.Currency;
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

        @Enumerated (EnumType.STRING)
        @Column (name = "currency_type")
        private Currency currency;

        private float exchangeRate;

        @OneToMany (mappedBy = "currencyType", fetch = FetchType.EAGER)
        private Set<Saldo> saldos;

}
