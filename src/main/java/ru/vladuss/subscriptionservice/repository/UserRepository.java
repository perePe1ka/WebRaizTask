package ru.vladuss.subscriptionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vladuss.subscriptionservice.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
