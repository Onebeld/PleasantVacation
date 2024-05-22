package com.onebeld.pleasantvacation.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onebeld.pleasantvacation.entity.ExchangeRate;
import com.onebeld.pleasantvacation.repository.ExchangeRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRateServiceImpl {
    /** Ссылка на API курса валют */
    private final String apiUrl = "Your_API";
    /** Шаблон, используемый для преобразования JSON ответа из API курса валют в объект Java */
    private final RestTemplate restTemplate = new RestTemplate();

    /** Репозиторий, позволяющий взаимодействовать с данными курс валют в базе данных */
    private final ExchangeRateRepository exchangeRateRepository;

    /** Конструктор, инициализирующий репозиторий */
    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /** Обновляет курсы валют на сервере */
    public void updateRates() {
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
