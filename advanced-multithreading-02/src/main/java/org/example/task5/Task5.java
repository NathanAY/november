package org.example.task5;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.example.task5.exception.AccountNotFoundException;
import org.example.task5.model.Account;
import org.example.task5.model.Currency;
import org.example.task5.service.AccountService;

public class Task5 {

  public void test() throws Exception {
    AccountService accountService = new AccountService();

    loadAccountsFromFiles(accountService);

    Currency currency = new Currency(); currency.setCode("USD"); currency.setName("Dollar");

    Set<Callable<Void>> callables = new HashSet<>();

    callables.add(() -> {
      System.out.println("Thread-1 started");
      int i = 1;
      while (i <= 100) {
        accountService.transfer(1, 2, new BigDecimal(1), currency);
        i++;
      }
      System.out.println("Thread-1 finished");
      return null;
    });
    callables.add(() -> {
      System.out.println("Thread-2 started");
      int i = 1;
      while (i <= 100) {
        accountService.transfer(3, 4, new BigDecimal(1), currency);
        i++;
      }
      System.out.println("Thread-2 finished");
      return null;
    });

    long startTime = System.currentTimeMillis();

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    executorService.invokeAll(callables);
    executorService.close();

    System.out.println("Execution time:" + (startTime - System.currentTimeMillis()));
    saveAccountsToFiles(accountService);
  }

  @SneakyThrows
  private void loadAccountsFromFiles(AccountService accountService) {
    Path resourceDirectory = Paths.get(Task5.class.getResource("/accounts").toURI());
    try (Stream<Path> paths = Files.walk(resourceDirectory)) {
      paths.filter(Files::isRegularFile)
          .forEach(path -> {
            // path;
            try {
              String id = path.getFileName().toString().split("\\.")[0].split("-")[1];
              String lines = Files.readAllLines(path).get(0);
              String[] values = lines.split(", ");
              ConcurrentHashMap<String, BigDecimal> currencyBalance = new ConcurrentHashMap<>(Map
                  .of(values[2], new BigDecimal(values[3])));
              accountService.createAccount(Long.valueOf(id), values[1], currencyBalance);
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          });
    }
  }

  @SneakyThrows
  private void saveAccountsToFiles(AccountService accountService) {
    long id = 1;
    while (true) {
      try {
        Account account = accountService.finAccount(id);
        System.out.println(account);
        Path resourceDirectory = Paths.get(Task5.class.getResource("/accounts/account-" + account.getId() + ".txt").toURI());
        PrintWriter out = new PrintWriter(resourceDirectory.toFile());
        BigDecimal amount = account.getCurrencyBalance().get("USD");
        StringBuilder sb = new StringBuilder().append(account.getId()).append(", ")
            .append(account.getName()).append(", ").append("USD").append(", ").append(amount);
        out.write(sb.toString());
        out.close();
        id++;
      } catch (AccountNotFoundException notFoundException) {
        return;
      }
    }
  }
}
