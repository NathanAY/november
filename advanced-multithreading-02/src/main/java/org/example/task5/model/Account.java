package org.example.task5.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Account {

  private Long id;
  private String name;
  private Map<String, BigDecimal> currencyBalance;

  public Account() {

  }

  public Account(Account other) {
    this.id = other.id;
    this.name = other.name;
    this.currencyBalance = new ConcurrentHashMap<>(other.currencyBalance);
  }

  public synchronized void withdraw(BigDecimal amount, Currency currency) {
    BigDecimal currencyBalance = getCurrencyBalance().get(currency.getCode());
    BigDecimal subtract = currencyBalance.subtract(amount);
    getCurrencyBalance().put(currency.getCode(), subtract);
  }

  public synchronized void deposit(BigDecimal amount, Currency currency) {
    BigDecimal currencyBalance = getCurrencyBalance().get(currency.getCode());
    BigDecimal added = currencyBalance.add(amount);
    getCurrencyBalance().put(currency.getCode(), added);
  }

}
