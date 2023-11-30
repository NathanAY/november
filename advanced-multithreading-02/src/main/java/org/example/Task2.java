package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Task2 {


  public void test() throws Exception {
    HashMap<Integer, Integer> map = new HashMap<>();

    new Thread(() -> {
      int i = 0;
      while (i >= 0) {
        synchronized (map) {
          map.put(i, i);
          i++;
          System.out.println("Added new number " + map);
        }
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();

    new Thread(() -> {
      System.out.println("reduce started");
      while (true) {
        synchronized (map) {
          Integer reduce = map.values().stream().reduce(0, (i1, i2) -> i1 + i2);
          System.out.println("Sum " + reduce);
        }
        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();

    new Thread(() -> {
      System.out.println("reduce started");
      while (true) {
        synchronized (map) {
          Integer reduce = map.values().stream().reduce(0, (i1, i2) -> Double
              .valueOf(Math.sqrt(i1)).intValue() + Double.valueOf(Math.sqrt(i2)).intValue());
          System.out.println("Sqrt of Sqrt " + Math.sqrt(reduce));
        }
        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();
  }


}
