package org.example.task5.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.example.task5.dao.AccountDao;
import org.example.task5.model.Account;
import org.example.task5.model.Currency;

public class AccountService {

  AccountDao accountDao = new AccountDao();
  private final Object transferLock = new Object();

  LockManager lockManager = new LockManager();

  class LockManager {
    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();

    public synchronized ReentrantLock acquireLock(Long id) {
      return locks.computeIfAbsent(id, k -> new ReentrantLock());
    }
  }

  public void createAccount(long id, String name, Map<String, BigDecimal> currencyBalance) {
    Account account = new Account();
    account.setId(id);
    account.setName(name);
    account.setCurrencyBalance(currencyBalance);
    accountDao.save(account);
  }

  public Account finAccount(long id) {
    return accountDao.load(id);
  }

  //Old with just plain synchronized. Slow transfer because only one thread could execute this
  // @SneakyThrows
  // public void transfer(long fromAccountId, long toAccountId, BigDecimal amount, Currency currency) {
  //   synchronized (transferLock) {
  //     Account fromAccount = accountDao.load(fromAccountId);
  //     Account toAccount = accountDao.load(toAccountId);
  //
  //     fromAccount.withdraw(amount, currency);
  //     toAccount.deposit(amount, currency);
  //
  //     accountDao.save(fromAccount);
  //     accountDao.save(toAccount);
  //     Thread.sleep(10);
  //   }
  // }

  @SneakyThrows
  public void transfer(long fromAccountId, long toAccountId, BigDecimal amount, Currency currency) {

    // Ensuring the smaller id will be locked first
    long firstId = Math.min(fromAccountId, toAccountId);
    long secondId = Math.max(fromAccountId, toAccountId);

    // Attempting to acquire locks
    if (lockManager.acquireLock(firstId).tryLock(5, TimeUnit.SECONDS)) {
      try {
        if (lockManager.acquireLock(secondId).tryLock(5, TimeUnit.SECONDS)) {
          try {
            // Load and check accounts only here
            Account fromAccount = accountDao.load(fromAccountId);
            Account toAccount = accountDao.load(toAccountId);

            fromAccount.withdraw(amount, currency);
            toAccount.deposit(amount, currency);

            accountDao.save(fromAccount);
            accountDao.save(toAccount);
            Thread.sleep(10);
          } finally {
            lockManager.acquireLock(secondId).unlock();
          }
        }
      } finally {
        lockManager.acquireLock(firstId).unlock();
      }
    }
  }

  public void withdraw(Account account, BigDecimal amount, Currency currency) {
    BigDecimal currencyBalance = account.getCurrencyBalance().get(currency.getCode());
    BigDecimal subtract = currencyBalance.subtract(amount);
    account.getCurrencyBalance().put(currency.getCode(), subtract);
  }

  public void deposit(Account account, BigDecimal amount, Currency currency) {
    BigDecimal currencyBalance = account.getCurrencyBalance().get(currency.getCode());
    BigDecimal added = currencyBalance.add(amount);
    account.getCurrencyBalance().put(currency.getCode(), added);
  }
}
