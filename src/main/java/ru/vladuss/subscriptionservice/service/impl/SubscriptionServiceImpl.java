package ru.vladuss.subscriptionservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladuss.subscriptionservice.dto.SubscriptionDto;
import ru.vladuss.subscriptionservice.dto.SubscriptionStatisticDto;
import ru.vladuss.subscriptionservice.entity.Subscription;
import ru.vladuss.subscriptionservice.entity.User;
import ru.vladuss.subscriptionservice.exception.DuplicateSubscriptionException;
import ru.vladuss.subscriptionservice.exception.ResourceNotFoundException;
import ru.vladuss.subscriptionservice.repository.SubscriptionRepository;
import ru.vladuss.subscriptionservice.repository.UserRepository;
import ru.vladuss.subscriptionservice.service.SubscriptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public SubscriptionDto createSubscription(UUID userUuid, String serviceName) {
        log.info("Creating subscription: userId={}, service={}", userUuid, serviceName);

        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> {
                    log.warn("User {} not found, cannot create subscription", userUuid);
                    return new ResourceNotFoundException("User %s not found".formatted(userUuid));
                });

        if (subscriptionRepository.existsByUserUuidAndServiceName(userUuid, serviceName)) {
            log.warn("Duplicate subscription attempt: user={}, service={}", userUuid, serviceName);
            throw new DuplicateSubscriptionException("User %s already subscribed to %s".formatted(userUuid, serviceName));
        }

        Subscription subscription = new Subscription();
        subscription.setServiceName(serviceName);
        subscription.setSubscribedAt(LocalDateTime.now());
        subscription.setUser(user);

        user.getSubscriptions().add(subscription);

        Subscription saved = subscriptionRepository.save(subscription);
        log.debug("Subscription created: {}", saved.getUuid());
        return toDto(saved);
    }

    @Override
    public List<SubscriptionDto> getUserSubscriptions(UUID userUuid) {
        log.debug("Listing subscriptions for user={}", userUuid);
        if (!userRepository.existsById(userUuid)) {
            log.warn("User {} not found, cannot list subscriptions", userUuid);
            throw new ResourceNotFoundException("User %s not found".formatted(userUuid));
        }
        return subscriptionRepository.findByUserUuid(userUuid)
                .stream()
                .map(SubscriptionServiceImpl::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void deleteSubscription(UUID userUuid, UUID subscriptionsUuid) {
        log.info("Deleting subscription {} for user {}", subscriptionsUuid, userUuid);
        Subscription subscription = subscriptionRepository.findById(subscriptionsUuid)
                .orElseThrow(() -> {
                    log.warn("Subscription {} not found", subscriptionsUuid);
                    return new ResourceNotFoundException("Sub - %s not found :{ ".formatted(subscriptionsUuid));
                });

        if (!subscription.getUser().getUuid().equals(userUuid)) {
            log.warn("Subscription {} does not belong to user {}", subscriptionsUuid, userUuid);
            throw new IllegalArgumentException("Sub - %s not belong to User - %s".formatted(subscriptionsUuid, userUuid));
        }

        subscriptionRepository.delete(subscription);
        log.debug("Subscription {} deleted", subscriptionsUuid);
    }

    @Override
    public List<SubscriptionStatisticDto> getThreeSubscriptions(int limit) {
        log.debug("Fetching TOP {} subscriptions", limit);
        return subscriptionRepository.findTopSubscriptions(PageRequest.of(0, limit));
    }

    private static SubscriptionDto toDto(Subscription s) {
        return new SubscriptionDto(s.getUuid(), s.getServiceName(), s.getSubscribedAt());
    }
}
