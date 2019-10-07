package com.ii.app.models;

import com.ii.app.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transaction_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "source_account_number")
    private String sourceAccountNumber;

    @Column(name = "source_currency")
    private String sourceCurrency;

    @Column(name = "destined_account_number")
    private String destinedAccountNumber;

    @Column(name = "destined_currency")
    private String destinedCurrency;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "title")
    private String title;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "modification_date")
    private Instant modificationDate;

    @Column(name = "multi_currency")
    private Boolean multiCurrency;

    @Column(name = "template_name")
    private String templateName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}
