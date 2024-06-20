package com.onebeld.pleasantvacation.repositories;

import com.onebeld.pleasantvacation.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface defines methods to access and manage the {@link ExchangeRate} entity in the database.
 * This interface extends {@link JpaRepository}, which provides a default implementation
 * for most CRUD operations (basic database operations: create,
 * read, edit, and delete).
 *
 * @see JpaRepository
 */
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    /**
     * Finds the exchange rate of a currency by its name.
     *
     * @param currency Currency name
     * @return The currency rate found in the database, or {@code null} if no such currency exists
     */
    ExchangeRate findByCurrency(String currency);
}
