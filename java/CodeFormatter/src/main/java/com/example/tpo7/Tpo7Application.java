package com.example.tpo7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Tpo7Application {

    public static void main(String[] args) {
        SpringApplication.run(Tpo7Application.class, args);
    }

}
