package com.onebeld.pleasantvacation.initializers;

import com.onebeld.pleasantvacation.entity.Role;
import com.onebeld.pleasantvacation.repository.ExchangeRateRepository;
import com.onebeld.pleasantvacation.repository.RoleRepository;
import com.onebeld.pleasantvacation.service.impl.ExchangeRateServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    private final ExchangeRateServiceImpl exchangeRateService;

    public DatabaseInitializer(RoleRepository roleRepository, ExchangeRateRepository exchangeRateRepository, ExchangeRateServiceImpl exchangeRateService) {
        this.roleRepository = roleRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public void run(String... args) {
        initializeRoles();
        initializeExchangeRates();
    }

    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role userRole = new Role(0, "USER");
            Role tourmanagerRole = new Role(1, "TOURMANAGER");

            roleRepository.save(userRole);
            roleRepository.save(tourmanagerRole);
        }
    }

    private void initializeExchangeRates() {
        if (exchangeRateRepository.count() == 0)
            exchangeRateService.updateRates();
    }
}
