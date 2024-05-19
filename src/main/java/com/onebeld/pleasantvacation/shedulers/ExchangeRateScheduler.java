package com.onebeld.pleasantvacation.shedulers;

import com.onebeld.pleasantvacation.service.impl.ExchangeRateServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateScheduler {
    private final ExchangeRateServiceImpl exchangeRateService;

    public ExchangeRateScheduler(ExchangeRateServiceImpl exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "PST")
    public void scheduleExchangeRateUpdate() {
        exchangeRateService.updateRates();
    }
}
