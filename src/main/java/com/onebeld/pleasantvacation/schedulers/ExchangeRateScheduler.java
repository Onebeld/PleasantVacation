package com.onebeld.pleasantvacation.schedulers;

import com.onebeld.pleasantvacation.services.ExchangeRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * A class responsible for scheduling tasks to update exchange rates.
 * <p>
 * Uses the {@link ExchangeRateService} service to perform rate update operations.
 * The @Component annotation indicates that the class is a Spring component and its instance
 * must be registered in the application context.
 */
@Component
public class ExchangeRateScheduler {
    private final ExchangeRateService exchangeRateService;

    /**
     * {@link ExchangeRateScheduler} constructor.
     *
     * @param exchangeRateService Service for updating exchange rates
     */
    ExchangeRateScheduler(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * The method is scheduled to run at 00:00 Pacific Time (PST) every day.
     * It calls the {@code updateRates()} method from {@link ExchangeRateService}, which updates the exchange rates on the server.
     *
     * @see ExchangeRateService#updateRates()
     */
    @Scheduled(cron = "0 0 0 * * ?", zone = "PST")
    void scheduleExchangeRateUpdate() {
        exchangeRateService.updateRates();
    }
}
