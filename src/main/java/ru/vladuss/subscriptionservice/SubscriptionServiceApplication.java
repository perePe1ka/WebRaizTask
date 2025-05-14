package ru.vladuss.subscriptionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubscriptionServiceApplication {

    public static void main(String[] args) {
        System.setProperty("server.port", "8086");
        SpringApplication.run(SubscriptionServiceApplication.class, args);
    }

}
