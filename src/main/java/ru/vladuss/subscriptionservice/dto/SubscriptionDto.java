package ru.vladuss.subscriptionservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionDto (
        UUID uuid,
        String serviceName,
        LocalDateTime localDateTime
){}
