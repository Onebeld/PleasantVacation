package com.onebeld.pleasantvacation.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onebeld.pleasantvacation.entities.ExchangeRate;
import com.onebeld.pleasantvacation.repositories.ExchangeRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Service for working with exchange rates
 */
@Service
public class ExchangeRateService {
    /**
     * Template used to convert a JSON response from the exchange rate API into a Java object
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * A repository to interact with exchange rate data in the database
     */
    private final ExchangeRateRepository exchangeRateRepository;

    /**
     * A constructor that initializes a repository
     */
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * Updates exchange rates on the server
     */
    public void updateRates() {
        String apiUrl = System.getenv("SPRING_EXCHANGE_RATE_URL");

        if (apiUrl == null)
            throw new RuntimeException("SPRING_EXCHANGE_RATE_URL environment variable is not set");

        // Получаем данные курса валют с API
        ApiResponse response = restTemplate.getForObject(apiUrl, ApiResponse.class);

        // Если ответ от API не получен, или данные пусты, мы не выполняем код дальше
        if (response == null || response.data == null)
            return;

        // Если в базе данных ещё нет данных курса валют, мы записываем их в базу данных
        if (exchangeRateRepository.count() == 0) {
            for (Map.Entry<String, ApiResponse.CurrencyData> entry : response.data.entrySet()) {
                exchangeRateRepository.save(new ExchangeRate(entry.getKey(), entry.getValue().getValue()));
            }
        }
        // Иначе изменяем уже существующие
        else {
            for (Map.Entry<String, ApiResponse.CurrencyData> entry : response.data.entrySet()) {
                ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(entry.getKey());
                exchangeRate.setRate(entry.getValue().getValue());
                exchangeRateRepository.save(exchangeRate);
            }
        }
    }

    /**
     * Converts the price in rubles
     *
     * @param price Price
     * @return Price in rubles
     */
    public double convertCurrency(double price) {
        double value = price * exchangeRateRepository.findByCurrency("RUB").getRate();

        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
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
