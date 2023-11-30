package org.example.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.SneakyThrows;

public class BlockingObjectPool {

  private final LinkedBlockingQueue<Object> pool;
  private final List<String> list;
  private final int maxSize;

  ReentrantLock lock = new ReentrantLock();
  Condition listEmptyCondition = lock.newCondition();
  Condition listFullCondition = lock.newCondition();


  public BlockingObjectPool(int size) {
    this.pool = new LinkedBlockingQueue<>(size);
    this.list = new ArrayList<>(size);
    this.maxSize = size;
  }

  @SneakyThrows
  public void add(String item) {
    try {
      lock.lock();
      while (list.size() == maxSize) {
        listFullCondition.await();
      }
      list.add(item);
      listEmptyCondition.signalAll();
    } finally {
      lock.unlock();
    }
  }

  @SneakyThrows
  public String get() {
    try {
      lock.lock();
      while (list.size() == 0) {
        listEmptyCondition.await();
      }
      return list.get(list.size() - 1);
    } finally {
      listFullCondition.signalAll();
      lock.unlock();

    }
  }
}