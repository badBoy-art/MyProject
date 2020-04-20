package com.thread.study;

import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
        System.out.println(list1);

        List<String> list2 = list1.stream().sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());

        list2.stream().forEach(System.out::println);

        Map<String, List<String>> map = list2.stream().collect(Collectors.groupingBy(String::toString));

        map.values().stream().forEach(System.out::println);

    }
}
