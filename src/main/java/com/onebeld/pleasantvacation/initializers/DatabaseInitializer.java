package com.onebeld.pleasantvacation.initializers;

import com.onebeld.pleasantvacation.entity.Role;
import com.onebeld.pleasantvacation.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public DatabaseInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role userRole = new Role(0, "USER");
            Role tourmanagerRole = new Role(1, "TOURMANAGER");

            roleRepository.save(userRole);
            roleRepository.save(tourmanagerRole);
        }
    }
}
