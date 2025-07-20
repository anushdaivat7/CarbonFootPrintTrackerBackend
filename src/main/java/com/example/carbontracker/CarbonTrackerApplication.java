package com.example.carbontracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarbonTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonTrackerApplication.class, args);
        System.out.println("CarbonTrackerApplication started");
    }

}
