package ru.vladuss.subscriptionservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vladuss.subscriptionservice.dto.SubscriptionStatisticDto;
import ru.vladuss.subscriptionservice.entity.Subscription;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    boolean existsByUserUuidAndServiceName(UUID userUuid, String serviceName);

    @Query("""
            select new ru.vladuss.subscriptionservice.dto.SubscriptionStatisticDto(s.serviceName, count(s))
            from Subscription s
            group by s.serviceName
            order by count(s) desc
            """)
    List<SubscriptionStatisticDto> findTopSubscriptions(Pageable pageable);

    List<Subscription> findByUserUuid(UUID userUuid);
}
