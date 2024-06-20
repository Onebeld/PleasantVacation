package com.onebeld.pleasantvacation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents the exchange rate of a currency.
 */
@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private double rate;

    /**
     * Default constructor.
     */
    public ExchangeRate() {
    }

    /**
     * Creates a new exchange rate with the specified currency and rate.
     *
     * @param currency Currency
     * @param rate     Rate
     */
    public ExchangeRate(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    /**
     * Gets the exchange rate identifier.
     *
     * @return Exchange rate identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the exchange rate identifier.
     *
     * @param id Exchange rate identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the exchange rate currency.
     *
     * @return Currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the exchange rate currency.
     *
     * @param currency Currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the exchange rate.
     *
     * @return Rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Sets the exchange rate.
     *
     * @param rate Rate
     */
    public void setRate(double rate) {
        this.rate = rate;
    }
}
