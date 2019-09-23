package com.ii.app.models.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table (name = "credit_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditStatus
{
        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated (EnumType.STRING)
        @Column (name = "credit_status")
        private CreditType creditType;

        public enum CreditType
        {
                PAID,
                CANCELED,
                ACTIVE,
                AWAITING
        }
}
