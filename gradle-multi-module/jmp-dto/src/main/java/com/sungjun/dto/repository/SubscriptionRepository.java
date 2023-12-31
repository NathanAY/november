package com.sungjun.dto.repository;

import com.sungjun.dto.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>,
    JpaSpecificationExecutor<Subscription> {
}
