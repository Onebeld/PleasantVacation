package com.onebeld.pleasantvacation.initializers;

import com.onebeld.pleasantvacation.entities.Role;
import com.onebeld.pleasantvacation.repositories.ExchangeRateRepository;
import com.onebeld.pleasantvacation.repositories.RoleRepository;
import com.onebeld.pleasantvacation.services.ExchangeRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for initializing the database with default data at application startup.
 * It implements the {@link CommandLineRunner} interface, which allows it to run custom code at startup.
 *
 * @see CommandLineRunner
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {
    /**
     * The {@link RoleRepository} dependency is used to interact with roles in the database.
     */
    private final RoleRepository roleRepository;
    /**
     * The {@link ExchangeRateRepository} dependency is used to interact with exchange rates in the database.
     */
    private final ExchangeRateRepository exchangeRateRepository;

    /**
     * The {@link ExchangeRateService} dependency is used to update exchange rates.
     */
    private final ExchangeRateService exchangeRateService;

    /**
     * Constructor for the DatabaseInitializer class.
     *
     * @param roleRepository         Dependency {@link RoleRepository}.
     * @param exchangeRateRepository Dependency {@link ExchangeRateRepository}.
     * @param exchangeRateService    Dependency {@link ExchangeRateService}.
     */
    public DatabaseInitializer(RoleRepository roleRepository, ExchangeRateRepository exchangeRateRepository, ExchangeRateService exchangeRateService) {
        this.roleRepository = roleRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * This method is called by the Spring Boot framework when the application starts.
     * It initializes roles and exchange rates in the database.
     *
     * @param args Command Line Arguments.
     */
    @Override
    public void run(String... args) {
        initializeRoles();
        initializeExchangeRates();
    }

    /**
     * This method initializes roles in the database if they have not already been initialized.
     */
    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role userRole = new Role(0, "USER");
            Role tourmanagerRole = new Role(1, "TOURMANAGER");

            roleRepository.save(userRole);
            roleRepository.save(tourmanagerRole);
        }
    }

    /**
     * This method initializes the exchange rates in the database if they have not already been initialized.
     */
    private void initializeExchangeRates() {
        if (exchangeRateRepository.count() == 0)
            exchangeRateService.updateRates();
    }
}
