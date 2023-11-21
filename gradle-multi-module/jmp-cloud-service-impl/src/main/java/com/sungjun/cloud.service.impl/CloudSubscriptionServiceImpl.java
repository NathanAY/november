package com.sungjun.cloud.service.impl;

import com.sungjun.cloud.service.SubscriptionApi;
import com.sungjun.dto.entity.BankCard;
import com.sungjun.dto.entity.Subscription;
import com.sungjun.dto.repository.SubscriptionRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CloudSubscriptionServiceImpl implements SubscriptionApi {

  private SubscriptionRepository subscriptionRepository;

  @Override
  public void subscribe(BankCard bankCard) {
    Subscription subscription = new Subscription();
    subscription.setBankcard(bankCard.getNumber());
    subscription.setStartDate(LocalDate.now(Clock.systemUTC()));
    subscriptionRepository.save(subscription);
  }

  @Override
  public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
    Specification specification = (Specification<Object>) (root, query, criteriaBuilder) -> {
      if (cardNumber == null) {
        return null;
      }
      return criteriaBuilder.equal(root.get("bankcard"), cardNumber);
    };

    return subscriptionRepository.findOne(specification);
}

  @Override
  public List<Subscription> getAllSubscriptionByCondition(Predicate<Subscription> predicate) {
    return null;
  }

}
