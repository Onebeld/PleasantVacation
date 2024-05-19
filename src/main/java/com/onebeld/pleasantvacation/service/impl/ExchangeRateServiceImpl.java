package com.onebeld.pleasantvacation.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onebeld.pleasantvacation.entity.ExchangeRate;
import com.onebeld.pleasantvacation.repository.ExchangeRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRateServiceImpl {
    private final String apiUrl = "https://api.currencyapi.com/v3/latest?apikey=YOUR_TOKEN";
    private final RestTemplate restTemplate = new RestTemplate();

    private ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public void updateRates() {
        ApiResponse response = restTemplate.getForObject(apiUrl, ApiResponse.class);

        if (response == null || response.data == null) {
            return;
        }

        if (exchangeRateRepository.count() == 0) {
            for (Map.Entry<String, ApiResponse.CurrencyData> entry : response.data.entrySet()) {
                exchangeRateRepository.save(new ExchangeRate(entry.getKey(), entry.getValue().getValue()));
            }
        }
        else {
            for (Map.Entry<String, ApiResponse.CurrencyData> entry : response.data.entrySet()) {
                ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(entry.getKey());
                exchangeRate.setRate(entry.getValue().getValue());
                exchangeRateRepository.save(exchangeRate);
            }
        }
    }

    private static class ApiResponse {
        private Meta meta;
        private Map<String, CurrencyData> data;

        public static class Meta {
            @JsonProperty("last_updated_at")
            private String lastUpdatedAt;

            public String getLastUpdatedAt() {
                return lastUpdatedAt;
            }

            public void setLastUpdatedAt(String lastUpdatedAt) {
                this.lastUpdatedAt = lastUpdatedAt;
            }
        }

        public static class CurrencyData {
            private String code;
            private double value;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }
        }

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public Map<String, CurrencyData> getData() {
            return data;
        }

        public void setData(Map<String, CurrencyData> data) {
            this.data = data;
        }
    }
}
