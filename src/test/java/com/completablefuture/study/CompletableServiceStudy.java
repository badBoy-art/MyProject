package com.completablefuture.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-10
 * @Description
 */
public class CompletableServiceStudy {

    @Test
    public void test() throws InterruptedException, ExecutionException {
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs = new
                ExecutorCompletionService<>(executor);
        // 用于保存Future对象List>
        List<Future<Integer>> futures = new ArrayList<>(3);
        // 异步向电商S1询价
        futures.add(cs.submit(() -> getPriceByS1()));
        // 异步向电商S2询价
        futures.add(cs.submit(() -> getPriceByS2()));
        // 异步向电商S3询价
        futures.add(cs.submit(() -> getPriceByS3()));
        // 将询价结果异步保存到数据库
        try {
            for (int i = 0; i < 3; i++) {
                Integer r = cs.take().get();
                if (r != null) {
                    executor.execute(() -> save(r));
                    break;
                }
            }
        } finally { //取消所有任务
            for (Future f : futures) {
                f.cancel(true);
            }
        }
    }


    private void save(Integer integer) {
        System.out.println(integer);
    }

    private Integer getPriceByS3() throws InterruptedException {
        Thread.sleep(2000);
        return 300;
    }

    private Integer getPriceByS2() throws InterruptedException {
        Thread.sleep(2000);
        return 200;
    }

    private Integer getPriceByS1() throws InterruptedException {
        Thread.sleep(2000);
        return 100;
    }

}
