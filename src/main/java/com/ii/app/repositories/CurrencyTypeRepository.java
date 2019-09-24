package com.ii.app.repositories;

import com.ii.app.models.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, Long>
{
        Optional<CurrencyType> findByName ( String name );

}
