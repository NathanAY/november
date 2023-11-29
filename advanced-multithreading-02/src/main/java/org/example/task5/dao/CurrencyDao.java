package org.example.task5.dao;

import java.util.ArrayList;
import java.util.List;
import org.example.task5.model.Currency;

public class CurrencyDao {

  private List<Currency> currencies = new ArrayList<>();

  public void save(Currency currency) {
    currencies.add(currency);
  }

  public Currency load(String code) {
    return currencies.stream()
        .filter(a -> a.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Currency not found"));
  }

}
