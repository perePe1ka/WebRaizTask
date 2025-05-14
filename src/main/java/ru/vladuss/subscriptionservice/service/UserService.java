package ru.vladuss.subscriptionservice.service;

import org.springframework.transaction.annotation.Transactional;
import ru.vladuss.subscriptionservice.entity.User;

import java.util.UUID;

public interface UserService {
    @Transactional
    User createUser(User user);

    User getUserById(UUID uuid);

    @Transactional
    User updateUser(UUID uuid, User updatedUser);

    @Transactional
    void deleteUser(UUID uuid);
}
