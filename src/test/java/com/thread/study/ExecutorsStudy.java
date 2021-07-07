package com.thread.study;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.stratio.cassandra.contrib.NotifyingBlockingThreadPoolExecutor;

import lombok.SneakyThrows;

/**
 * Executors Study
 *
 * @author xuedui.zhao
 * @create 2018-03-31
 *
 * <a href="https://mp.weixin.qq.com/s/zm2kSGTOmukgVmh4E8vbBQ"></a>
 */
public class ExecutorsStudy {

    @Test
    public void testExecutors() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");

        executorService.invokeAll(callables)
                .stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).forEach(System.out::println);

    }

    @Test
    public void testCollection() {
        List<String> list = Lists.newArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("d");

        List<String> list1 = list.stream().filter(a -> !"a".equals(a)).collect(Collectors.toList());
        list = list.stream().filter(a -> !"a".equals(a)).collect(Collectors.toList());
        System.out.println(list);
        System.out.println(list1);

        List<String> list2 = list1.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        list2.stream().forEach(System.out::println);

        Map<String, List<String>> map = list2.stream().collect(Collectors.groupingBy(String::toString));

        map.values().stream().forEach(System.out::println);

    }

    /**
     * @see NotifyingBlockingThreadPoolExecutor
     */
    @Test
    public void testNotifyingBlockingThreadPoolExecutor() {
        NotifyingBlockingThreadPoolExecutor threadPoolExecutor = new NotifyingBlockingThreadPoolExecutor(5, 10, 15, TimeUnit.SECONDS);
        for (int i = 0; i < 50; i++) {
            threadPoolExecutor.execute(new AThread(i));
        }
        try {
            threadPoolExecutor.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Done!");
    }

    /**
     * @see NotifyingBlockingThreadPoolExecutor
     */
    @Test
    public void testNotifyingBlockingThreadPoolExecutor2() {
        NotifyingBlockingThreadPoolExecutor threadPoolExecutor = new
                NotifyingBlockingThreadPoolExecutor(5, 10, 15, TimeUnit.SECONDS,
                1, TimeUnit.SECONDS, () -> false);
        for (int i = 0; i < 50; i++) {
            threadPoolExecutor.execute(new AThread(i));
        }
        try {
            threadPoolExecutor.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Done!");
    }


}

class AThread implements Runnable {

    private int arg;

    public AThread(int arg) {
        this.arg = arg;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println(arg);
        Thread.sleep(1000);
    }
}
