package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
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
        System.out.println(map);
        // try {
          // Thread.sleep(10);
        // } catch (InterruptedException e) {
        //   throw new RuntimeException(e);
        // }
      }
    }).start();

    Object call = ((Callable) () -> {
      System.out.println("reduce started");
      while (true) {
        Integer reduce = map.values().stream().reduce(0, (i1, i2) -> i1 + i2);
        System.out.println(reduce);
        // Thread.sleep(5);
      }
    }).call();
  }

  class ThreadSafeMap {
    HashMap<Integer, Integer> hashMap = new HashMap();

    public void put(Integer key, Integer value) {
      synchronized (hashMap) {
        hashMap.put(key, value);
      }
    }

    public Collection<Integer> values() {
      synchronized (hashMap) {
        List<Integer> cloneCollection = hashMap.values().stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList());
        return cloneCollection;
      }
    }
  }

}
