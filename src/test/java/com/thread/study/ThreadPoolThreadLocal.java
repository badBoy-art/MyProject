package com.thread.study;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPool处理threadLocal的方法
 *
 * @author badBoy
 * @create 2019-09-09
 */
public class ThreadPoolThreadLocal {


    @Test
    public void TestInh() {
        InheritableThreadLocal threadLocal = new InheritableThreadLocal();
        threadLocal.set("zhangsan");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("========");
                System.out.println(threadLocal.get());
            }
        };

        new Thread(runnable).start();
    }

    @Test
    public void TestThreadPoolInh() throws Exception {
        InheritableThreadLocal threadLocal = new InheritableThreadLocal();
        threadLocal.set("zhangsan");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("========" + Thread.currentThread().getName());
                System.out.println(threadLocal.get());
                threadLocal.set("lisi");
                System.out.println(threadLocal.get());
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(runnable);
        TimeUnit.SECONDS.sleep(1);
        executorService.submit(runnable);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("========" + Thread.currentThread().getName());
        System.out.println(threadLocal.get());
    }

    @Test
    public void testTransmittableThreadLocal() throws Exception{
        TransmittableThreadLocal<String> parent = new TransmittableThreadLocal<String>();
        parent.set("value-set-in-parent");
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("========" + Thread.currentThread().getName());
                System.out.println(parent.get());
                parent.set("lisi");
                System.out.println(parent.get());
            }
        };
        // 额外的处理，生成修饰了的对象ttlRunnable
        Runnable ttlRunnable = TtlRunnable.get(task);
        executorService.submit(ttlRunnable);
        TimeUnit.SECONDS.sleep(1);
        executorService.submit(ttlRunnable);
        TimeUnit.SECONDS.sleep(1);
        // Task中可以读取, 值是"value-set-in-parent"
        String value = parent.get();
        System.out.println("========" + Thread.currentThread().getName() + " " + value);
    }
}
