package ru.vladuss.subscriptionservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladuss.subscriptionservice.entity.User;
import ru.vladuss.subscriptionservice.exception.ResourceNotFoundException;
import ru.vladuss.subscriptionservice.repository.UserRepository;
import ru.vladuss.subscriptionservice.service.UserService;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User createUser(User user) {
        log.info("Creating user: name={}, email={}", user.getName(), user.getEmail());
        User saved = userRepository.save(user);
        log.debug("User created: {}", saved);
        return saved;
    }

    @Override
    public User getUserById(UUID uuid) {
        log.debug("Fetching user by id={}", uuid);
        return userRepository.findById(uuid)
                .orElseThrow(() -> {
                    log.warn("User {} not found", uuid);
                    return new ResourceNotFoundException("User - %s not found :( ".formatted(uuid));
                });
    }

    @Transactional
    @Override
    public User updateUser(UUID uuid, User updatedUser) {
        log.info("Updating user {} -> name={}, email={}", uuid, updatedUser.getName(), updatedUser.getEmail());
        User user = getUserById(uuid);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        User saved = userRepository.save(user);
        log.debug("User {} updated successfully", uuid);
        return saved;
    }

    @Transactional
    @Override
    public void deleteUser(UUID uuid) {
        log.info("Deleting user {}", uuid);
        if (!userRepository.existsById(uuid)) {
            log.warn("Delete failed: user {} not found", uuid);
            throw new ResourceNotFoundException("User - %s not found :[ ".formatted(uuid));
        }
        userRepository.deleteById(uuid);
        log.debug("User {} deleted", uuid);
    }
}
