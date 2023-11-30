package org.example.task3;

public class Consumer extends Thread {
  private CustomBlockingQueue queue;
  private String topic;

  public Consumer(CustomBlockingQueue queue, String topic) {
    this.queue = queue;
    this.topic = topic;
  }

  public void run() {
    while (true) {
      try {
        Message message = queue.poll(topic);
        if (message != null && message.getTopic().equals(topic)) {
          System.out.println(Thread.currentThread().getName() + " consumer poll from topic "
              + "[" + topic + "] "+ message.getPayload());
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
