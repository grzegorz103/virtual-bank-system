package com.ii.app.repositories;

import com.ii.app.models.CurrencyType;
import com.ii.app.models.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, Long>
{
        Optional<CurrencyType> findByCurrency ( Currency currency );

}
