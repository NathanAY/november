package org.example.task3;

import java.util.HashMap;

public class Task3 {


  public void test() throws Exception {
    CustomBlockingQueue queue = new CustomBlockingQueue(20);
    int producersSize = 2;
    int consumersSize = 2;

    for (int i = 0; i < producersSize; i++) {
      Producer producer = new Producer(queue);
      producer.start();
    }

    for (int i = 0; i < consumersSize; i++) {
      Consumer consumer = new Consumer(queue, "topic" + i);
      consumer.start();
    }
  }


}
