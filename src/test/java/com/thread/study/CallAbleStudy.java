package com.thread.study;

import com.google.common.collect.Lists;
import org.apache.lucene.util.NamedThreadFactory;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * callAble多线程学习
 *
 * @author xuedui.zhao
 * @create 2018-03-25
 */
public class CallAbleStudy {

  @Test
  public void testCallAble() {
    Long start = System.currentTimeMillis();
    MyCallable callable1 = new MyCallable(2000);
    MyCallable callable2 = new MyCallable(1000);

    List<MyCallable> callables = Lists.newArrayList();
    List<Future<List<String>>> futures = Lists.newArrayList();
    callables.add(callable1);
    callables.add(callable2);
    NamedThreadFactory tf = new NamedThreadFactory("Thread-");
    ThreadPoolExecutor threadPoolExecutor =
        new ThreadPoolExecutor(
            0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),tf);

    try {
      futures.add(threadPoolExecutor.submit(callable1));
      futures.add(threadPoolExecutor.submit(callable2));
      System.out.println(futures.size());
      for (Future<List<String>> future : futures) {
        try {
          System.out.println(future.get(40,TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
          System.out.println("cancel");
          future.cancel(true);
        }

      }

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    System.out.print(System.currentTimeMillis() - start);
  }

  @Test
  public void test() {
    ThreadLocal threadLocal = new ThreadLocal();
    threadLocal.set("zhangsan");
    threadLocal.get();
  }
}

class MyCallable implements Callable<List<String>> {
  private long waitTime;

  public MyCallable(int timeInMillis) {
    this.waitTime = timeInMillis;
  }

  @Override
  public List<String> call() throws Exception {
    List<String> list = Lists.newArrayList();
    for (int i = 0; i < 10; i++) {
      Thread.sleep(waitTime);
      list.add(Thread.currentThread().getName() + " " + i);
    }

    return list;
  }
}
