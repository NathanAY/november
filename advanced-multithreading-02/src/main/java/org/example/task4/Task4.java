package org.example.task4;


public class Task4 {


  public void test() throws Exception {
    BlockingObjectPool pool = new BlockingObjectPool(5);

    new Thread(() -> {
      int i = 0;
      while (i >= 0) {
        try {
          pool.take(new String("" + i++));
          Thread.sleep(10);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();

    new Thread(() -> {
      int i = 0;
      while (i >= 0) {
        try {
          pool.take(new String("" + i++));
          Thread.sleep(30);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();

    new Thread(() -> {
      System.out.println("consumer started");
      while (true) {
        try {
          Object obj = pool.get();
          System.out.println("obj " + obj);
          Thread.sleep(50);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();
  }

}
