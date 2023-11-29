package org.example.task5;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.example.task5.model.Currency;
import org.example.task5.service.AccountService;

public class Task5 {


  public void test() throws Exception {
    AccountService accountService = new AccountService();

    accountService.createAccount(1, "PK", new ConcurrentHashMap<>(Map
        .of("USD", new BigDecimal(500))));
    accountService.createAccount(2, "KMFA", new ConcurrentHashMap<>(Map
        .of("USD", new BigDecimal(2000))));
    accountService.createAccount(3, "META", new ConcurrentHashMap<>(Map
        .of("USD", new BigDecimal(2500))));
    accountService.createAccount(4, "GOOG", new ConcurrentHashMap<>(Map
        .of("USD", new BigDecimal(1500))));

    Currency currency = new Currency();
    currency.setCode("USD");
    currency.setName("Dollar");

    Thread thread1 = new Thread(() -> {
      System.out.println("Thread-1 started");
      int i = 1;
      while (i <= 100) {
          accountService.transfer(1, 2, new BigDecimal(1), currency);
        i++;
      }

      System.out.println("Thread-1 finished");
    });

    Thread thread2 = new Thread(() -> {
      System.out.println("Thread-2 started");
      int i = 1;
      while (i <= 100) {
          accountService.transfer(3, 4, new BigDecimal(1), currency);
        i++;
      }

      System.out.println("Thread-2 finished");
    });

    long startTime = System.currentTimeMillis();

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    System.out.println("Execution time:" + (startTime - System.currentTimeMillis()));

    System.out.println(accountService.finAccount(1L));
    System.out.println(accountService.finAccount(2L));
    System.out.println(accountService.finAccount(3L));
    System.out.println(accountService.finAccount(4L));
  }


}
