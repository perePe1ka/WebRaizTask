package ru.vladuss.subscriptionservice.dto;

public record SubscriptionStatisticDto(
        String serviceName,
        long count
) {}
