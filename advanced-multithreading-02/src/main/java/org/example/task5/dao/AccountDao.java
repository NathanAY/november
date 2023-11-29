package org.example.task5.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.example.task5.exception.AccountNotFoundException;
import org.example.task5.model.Account;

public class AccountDao {

  List<Account> accountList = new ArrayList<>();

  public synchronized void save(Account account) {
    AtomicReference<Account> accountRef = new AtomicReference<>();
    accountList.replaceAll(a -> {
      if (a.getId().equals(account.getId())) {
        accountRef.getAndSet(account);
        return account;
      }
      return a;
    });

    if (accountRef.get() == null) {
      accountList.add(account);
    }
  }

  public synchronized Account load(Long id) {
    return accountList.stream()
        .filter(account -> account.getId().equals(id))
        .findFirst()
        .map(Account::new)
        .orElseThrow(() -> new AccountNotFoundException());
  }

}
