package com.sungjun.cloud.service;

import com.sungjun.dto.entity.BankCard;
import com.sungjun.dto.entity.Subscription;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface SubscriptionApi {

  void subscribe(BankCard bankCard);
  Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);
  List<Subscription> getAllSubscriptionByCondition(Predicate<Subscription> predicate);

}
