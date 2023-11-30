package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class Task1 {


  public void test() throws Exception {
    // HashMap<Integer, Integer> map = new HashMap<>();
    // ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
    // Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());
    ThreadSafeMap map = new ThreadSafeMap();

    new Thread(() -> {
      int i = 0;
      while (i >= 0) {
        map.put(i, i);
        i++;
        System.out.println("add");
        // try {
        //   Thread.sleep(10);
        // } catch (InterruptedException e) {
        //   throw new RuntimeException(e);
        // }
      }
    }).start();

    new Thread(() -> {
      System.out.println("reduce started");
      while (true) {
        Integer reduce = map.values().stream().reduce(0, (i1, i2) -> i1 + i2);
        System.out.println(reduce);
        // Thread.sleep(5);
      }
    }).start();
  }

  class ThreadSafeMap {
    HashMap<Integer, Integer> hashMap = new HashMap();

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void put(Integer key, Integer value) {
      writeLock.lock();
      try {
        hashMap.put(key, value);
      } finally {
        writeLock.unlock();
      }
    }

    public Collection<Integer> values() {
      readLock.lock();
      try {
        List<Integer> cloneCollection = hashMap.values().stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList());
        return cloneCollection;
      } finally {
        readLock.unlock();
      }
    }
  }

}
