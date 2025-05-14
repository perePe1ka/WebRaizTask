package ru.vladuss.subscriptionservice.service;

import org.springframework.transaction.annotation.Transactional;
import ru.vladuss.subscriptionservice.dto.SubscriptionDto;
import ru.vladuss.subscriptionservice.dto.SubscriptionStatisticDto;
import ru.vladuss.subscriptionservice.entity.Subscription;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    @Transactional
    SubscriptionDto createSubscription(UUID userUuid, String name);

    List<SubscriptionDto> getUserSubscriptions(UUID userUuid);

    @Transactional
    void deleteSubscription(UUID userUuid, UUID subscriptionsUuid);

    List<SubscriptionStatisticDto> getThreeSubscriptions(int limit);
}
