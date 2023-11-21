package com.sungjun.admin.rest;

import com.sungjun.bank.api.BankApi;
import com.sungjun.cloud.service.SubscriptionApi;
import com.sungjun.cloud.service.UserService;
import com.sungjun.dto.entity.BankCard;
import com.sungjun.dto.entity.BankCardType;
import com.sungjun.dto.entity.Subscription;
import com.sungjun.dto.entity.UserEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AppController {

  private UserService userService;
  private SubscriptionApi subscriptionApi;
  private BankApi bankApi;

  /**
   * @param name
   * @param surname
   * @param birthDay in format 1995-10-22
   * @return
   */
  @GetMapping("/createUser")
  public Object getUser(@RequestParam String name, @RequestParam String surname, @RequestParam String birthDay) {
    UserEntity user = userService.createUser(name, surname, LocalDate.parse(birthDay));
    return user;
  }

  @GetMapping("/createBankCard")
  public BankCard getBankCard(@RequestParam long userId, @RequestParam String cardType) {
    Optional<UserEntity> user = userService.getAllUsers().stream()
        .filter(userEntity -> userEntity.getId().equals(userId))
        .findAny();

    AtomicReference<BankCard> bankCard = new AtomicReference<>();

    user.ifPresent(userEntity -> {
      BankCard card = bankApi.createBankCard(userEntity, BankCardType.valueOf(cardType));
      subscriptionApi.subscribe(card);
      bankCard.set(card);
    });

    return bankCard.get();
  }

  @GetMapping("/getAllUsers")
  public List<UserEntity> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/getAverageUsersAge")
  public Double getAverageUsersAge() {
    return userService.getAverageUsersAge();
  }

  @SneakyThrows
  @GetMapping("/isPayable")
  public boolean isPayable(@RequestParam long userId) {
    Optional<UserEntity> optionalUser = userService.getAllUsers().stream()
        .filter(userEntity -> userEntity.getId().equals(userId))
        .findAny();

    UserEntity user = optionalUser.orElseThrow(
        () -> new NotFoundException()
    );
    return UserService.isPayableUserAge(user);
  }

  @SneakyThrows
  @GetMapping("/getSubscription")
  public Subscription getSubscription(@RequestParam String cardNumber) {
    return subscriptionApi.getSubscriptionByBankCardNumber(cardNumber).orElseThrow(() -> new NotFoundException());
  }
}
