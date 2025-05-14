package ru.vladuss.subscriptionservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
@ToString(exclude = "subscriptions")
public class User extends BaseEntity{

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Subscription> subscriptions = new HashSet<>();

}
