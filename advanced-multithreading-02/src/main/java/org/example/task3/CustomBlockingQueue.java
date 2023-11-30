package org.example.task3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CustomBlockingQueue {
  // private Queue<T> queue = new LinkedList<>();
  private Map<String, Queue<Message>> queue2 = new HashMap<>();
  private int maxSize;

  public CustomBlockingQueue(int maxSize) {
    queue2.put("topic0", new LinkedList<>());
    queue2.put("topic1", new LinkedList<>());
    queue2.put("topic2", new LinkedList<>());
    queue2.put("topic3", new LinkedList<>());
    queue2.put("topic4", new LinkedList<>());
    queue2.put("topic5", new LinkedList<>());
    queue2.put("topic6", new LinkedList<>());
    queue2.put("topic7", new LinkedList<>());
    queue2.put("topic8", new LinkedList<>());
    queue2.put("topic9", new LinkedList<>());
    this.maxSize = maxSize;
  }

  public synchronized void add(Message item) throws InterruptedException {
    while (queue2.get(item.getTopic()).size() >= maxSize) {
      System.out.println(Thread.currentThread().getName() + " WAIT FROM ADD");
      wait();
    }
    if (queue2.get(item.getTopic()).isEmpty()) {
      System.out.println(Thread.currentThread().getName() + " NOTIFY ALL FROM ADD");
      notifyAll();
    }
    queue2.get(item.getTopic()).add(item);
    System.out.print(item.getTopic() + " queue size " + queue2.get(item.getTopic()).size() + " ");
  }

  public synchronized Message poll(String topic) throws InterruptedException {
    while (queue2.get(topic).isEmpty()) {
      System.out.println(Thread.currentThread().getName() + " WAIT FROM POLL");
      wait();
    }
    if (queue2.get(topic).size() == maxSize) {
      System.out.println(Thread.currentThread().getName() + " NOTIFY ALL FROM POLL");
      notifyAll();
    }
    return queue2.get(topic).poll();
  }
}