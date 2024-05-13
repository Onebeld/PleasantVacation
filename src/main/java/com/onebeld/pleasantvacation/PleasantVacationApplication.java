package com.onebeld.pleasantvacation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    /*@Bean
    public CommandLineRunner run(UserRepository userRepository) {
        return (args) -> {
            userRepository.save(new User("Иванов",
                    "Иван",
                    "Иванович",
                    "ivanov@example.com",
                    "12345",
                    "Таганрог",
                    "Россия", null));
        };
    }*/
}
