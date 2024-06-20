package com.onebeld.pleasantvacation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main class of the PleasantVacation application.
 * <p>
 * This class contains the main method that starts the Spring Boot application.
 * <p>
 * The @SpringBootApplication annotation indicates that this is the main class,
 * that supports autoconfiguration and component-scanning in Spring.
 * <p>
 * The @EnableScheduling annotation includes support for task scheduling.
 */
@SpringBootApplication
@EnableScheduling
public class PleasantVacationApplication {
    public static void main(String[] args) {
        SpringApplication.run(PleasantVacationApplication.class, args);
    }
}
