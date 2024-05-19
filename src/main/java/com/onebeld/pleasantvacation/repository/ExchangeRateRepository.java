package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findByCurrency(String currency);
}
