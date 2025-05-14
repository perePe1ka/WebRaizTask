package ru.vladuss.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vladuss.subscriptionservice.dto.SubscriptionDto;
import ru.vladuss.subscriptionservice.dto.UserDto;
import ru.vladuss.subscriptionservice.entity.User;
import ru.vladuss.subscriptionservice.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        log.info("POST /users – body={}", user);
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable UUID id) {
        log.info("GET /users/{}", id);
        return toDto(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable UUID id, @RequestBody User user) {
        log.info("PUT /users/{} – body={}", id, user);
        return toDto(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        log.info("DELETE /users/{}", id);
        userService.deleteUser(id);
    }

    private static UserDto toDto(User u) {
        return new UserDto(
                u.getUuid(),
                u.getName(),
                u.getEmail(),
                u.getSubscriptions().stream()
                        .map(s -> new SubscriptionDto(s.getUuid(), s.getServiceName(), s.getSubscribedAt()))
                        .toList()
        );
    }
}
