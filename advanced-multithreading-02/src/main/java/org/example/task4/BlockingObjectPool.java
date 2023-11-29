package org.example.task4;

import java.util.concurrent.LinkedBlockingQueue;

public class BlockingObjectPool {

  private final LinkedBlockingQueue<Object> pool;

  public BlockingObjectPool(int size) {
    pool = new LinkedBlockingQueue<>(size);
    for (int i = 0; i < size; i++) {
      pool.offer(new Object());
    }
  }

  public Object get() throws InterruptedException {
    return pool.take();
  }

  public void take(Object object) throws InterruptedException {
    pool.put(object);
    System.out.println("new object taken. Current size: " + pool.size());
  }
}