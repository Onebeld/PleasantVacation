package com.onebeld.pleasantvacation;

import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.entity.User;
import com.onebeld.pleasantvacation.entity.enums.Role;
import com.onebeld.pleasantvacation.entity.enums.TripState;
import com.onebeld.pleasantvacation.repository.TripRepository;
import com.onebeld.pleasantvacation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

@SpringBootApplication
public class PleasantVacationApplication {
    public static void main(String[] args) {
        SpringApplication.run(PleasantVacationApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner run(TripRepository tripRepository) {
        return (args) -> {
            tripRepository.save(new Trip("Таганрог", "Description example", "Таганрог", "Россия", Date.valueOf("2022-01-01"), Date.valueOf("2022-01-01"), 50_000, false, TripState.ACTIVE));

            System.out.println(tripRepository.findAll());
        };
    }*/
}
