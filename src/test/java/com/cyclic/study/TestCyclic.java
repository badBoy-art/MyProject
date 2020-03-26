package com.cyclic.study;

import org.junit.Test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 高并发测试
 *
 * @author xuedui.zhao
 * @create 2018-07-13
 */
public class TestCyclic {

  @Test
  public void test() {
    int count = 10;
    CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
    ExecutorService executorService = Executors.newFixedThreadPool(count);
    int n = 0;
    for (int i = 0; i < count; i++) {
      executorService.execute(new Task(cyclicBarrier, n));
      n++;
    }
    executorService.shutdown();
    while (!executorService.isTerminated()) {
      try {
        System.out.println("=====is sleep=====");
        Thread.sleep(100000);
        System.out.println("=====is wake=====");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class Task implements Runnable {
  private CyclicBarrier cyclicBarrier;
  int n = 0;

  public Task(CyclicBarrier cyclicBarrier, int n) {
    this.cyclicBarrier = cyclicBarrier;
    this.n = n;
  }

  @Override
  public void run() {
    try {
      System.out.println("赛马" + n + "到达栅栏前");
      cyclicBarrier.await();
      System.out.println("赛马" + n + "开始跑");
      System.out.println("hello:" + n);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
