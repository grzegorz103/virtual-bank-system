package com.ii.app.repositories;

import com.ii.app.models.ExchangeCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeCurrencyRepository extends JpaRepository<ExchangeCurrency, Long>
{
}
