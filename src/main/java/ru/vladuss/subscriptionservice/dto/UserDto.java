package ru.vladuss.subscriptionservice.dto;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID uuid,
        String name,
        String email,
        List<SubscriptionDto> subscriptions
) { }