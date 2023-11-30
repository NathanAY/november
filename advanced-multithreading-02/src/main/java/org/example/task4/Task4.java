package org.example.task4;


public class Task4 {


  public void test() throws Exception {
    BlockingObjectPool pool = new BlockingObjectPool(5);

    new Thread(() -> {
      int i = 0;
      while (i >= 0) {
        pool.add(new String("" + i++));
      }
    }).start();

    new Thread(() -> {
      int i = 0;
      while (i >= 0) {
        pool.add(new String("" + i++));
      }
    }).start();

    new Thread(() -> {
      System.out.println("consumer started");
      while (true) {
        Object obj = pool.get();
        System.out.println("obj " + obj);
      }
    }).start();
  }

}
