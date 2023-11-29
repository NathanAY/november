package org.example.task3;

import java.util.Random;

public class Producer extends Thread {
  private CustomBlockingQueue queue;

  public Producer(CustomBlockingQueue queue) {
    this.queue = queue;
  }

  public void run() {
    Random random = new Random();
    while (true) {
      try {
        String topic = "topic" + random.nextInt(10);
        String payload = "message_payload_" + random.nextInt(100);
        Message message = new Message(topic, payload);
        queue.add(message);
        System.out.println(Thread.currentThread().getName() + " producer put message " + message);
        Thread.sleep(random.nextInt(250));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
