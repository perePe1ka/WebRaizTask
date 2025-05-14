package ru.vladuss.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vladuss.subscriptionservice.dto.SubscriptionDto;
import ru.vladuss.subscriptionservice.dto.SubscriptionStatisticDto;
import ru.vladuss.subscriptionservice.entity.Subscription;
import ru.vladuss.subscriptionservice.service.SubscriptionService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{userId}/subscriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionDto create(@PathVariable UUID userId,
                                  @RequestBody Map<String, String> body) {
        String serviceName = body.get("serviceName");
        log.info("POST /users/{}/subscriptions – serviceName={}", userId, serviceName);
        return subscriptionService.createSubscription(userId, serviceName);
    }

    @GetMapping("/users/{userId}/subscriptions")
    public List<SubscriptionDto> list(@PathVariable UUID userId) {
        log.info("GET /users/{}/subscriptions", userId);
        return subscriptionService.getUserSubscriptions(userId);
    }

    @DeleteMapping("/users/{userId}/subscriptions/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId,
                       @PathVariable UUID subId) {
        log.info("DELETE /users/{}/subscriptions/{}", userId, subId);
        subscriptionService.deleteSubscription(userId, subId);
    }

    @GetMapping("/subscriptions/top")
    public List<SubscriptionStatisticDto> top(@RequestParam(defaultValue = "3") int limit) {
        log.info("GET /subscriptions/top?limit={}", limit);
        return subscriptionService.getThreeSubscriptions(limit);
    }
}
