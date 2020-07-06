package com.completablefuture.study;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * 时间通知 类似于guava ListenableFuture
 * <p>
 * * @author xuedui.zhao
 *
 * @create 2018-11-02
 */
public class CompletableFutureStudy {

    @Test
    public void test01() throws Exception {
        CompletableFuture<String> completableFuture = new CompletableFuture();

        //自己开个线程去执行 执行完把结果告诉completableFuture即可
        new Thread(() -> {
            // 模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 告诉completableFuture任务已经完成 并且把结果告诉completableFuture
            //这里把你信任的结果set进去后，所有阻塞的get()方法都能立马苏醒，获得到结果
            completableFuture.complete("ok");
        }).start();

        // 获取任务结果，如果没有完成会一直阻塞等待
        System.out.println("准备打印结果...");
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }

    @Test
    public void test02() {
        try {
            CompletableFuture<String> completableFuture = new CompletableFuture();
            new Thread(() -> {
                // 模拟执行耗时任务
                System.out.println("task doing...");
                try {
                    Thread.sleep(3000);
                    System.out.println(1 / 0);
                } catch (Exception e) {
                    System.out.println("InterruptedException");
                    completableFuture.completeExceptionally(e);
                }
                completableFuture.complete("ok");
            }).start();
            // 获取任务结果，如果没有完成会一直阻塞等待
            System.out.println("准备打印结果...");
            String result = completableFuture.get();
            System.out.println("计算结果:" + result);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        } catch (ExecutionException e) {
            System.out.println("ExecutionException");
        }
    }

    @Test
    public void test03() {
        CompletableFuture.supplyAsync(() -> 100)
                .thenApplyAsync(i -> i * 10)
                .thenApply(i -> i.toString())
                .whenComplete((r, e) -> System.out.println(r + "_____" + e));

        //若有异常
        CompletableFuture.supplyAsync(() -> 1 / 0)
                .thenApplyAsync(i -> i * 10)
                .thenApply(i -> i.toString())
                .whenComplete((r, e) -> System.out.println(r + "_____" + e));

    }

    @Test
    public void test04() {
        Random rand = new Random();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        CompletableFuture<String> f = future2.applyToEither(future, i -> i.toString());
        System.out.println(f.join());
    }

    @Test
    public void test05() {
        Random rand = new Random();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 300;
        });
        CompletableFuture<Object> f = CompletableFuture.anyOf(future1, future2, future3);
        System.out.println(f.join());
    }

    @Test
    public void test06() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "f1";
        });

        f1.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(System.currentTimeMillis() + ":" + s);
            }
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "f2";
        });

        f2.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(System.currentTimeMillis() + ":" + s);
            }
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);

        //阻塞，直到所有任务结束。
        System.out.println(System.currentTimeMillis() + ":阻塞");
        all.join();
        System.out.println(System.currentTimeMillis() + ":阻塞结束");

        //一个需要耗时2秒，一个需要耗时3秒，只有当最长的耗时3秒的完成后，才会结束。
        System.out.println("任务均已完成。");

    }

    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));

        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.<T>toList()));
    }

    @Test
    public void test07() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        CompletableFuture<List<Integer>> resultList = sequence(Arrays.asList(future1, future2));

        System.out.println(resultList.join());
    }

    @Test
    public void test08() {
        long start = System.nanoTime();
        //任务1：洗水壶->烧开水
        CompletableFuture<Void> f1 =
                CompletableFuture.runAsync(() -> {
                    System.out.println("T1:洗水壶...");
                    sleep(1, TimeUnit.SECONDS);

                    System.out.println("T1:烧开水...");
                    sleep(15, TimeUnit.SECONDS);
                });
        //任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("T2:洗茶壶...");
                    sleep(1, TimeUnit.SECONDS);

                    System.out.println("T2:洗茶杯...");
                    sleep(2, TimeUnit.SECONDS);

                    System.out.println("T2:拿茶叶...");
                    sleep(1, TimeUnit.SECONDS);
                    return "龙井";
                });
        //任务3：任务1和任务2完成后执行：泡茶
        CompletableFuture<String> f3 =
                f1.thenCombineAsync(f2, (__, tf) -> {
                    System.out.println("T1:拿到茶叶:" + tf);
                    System.out.println("T1:泡茶...");
                    return "上茶:" + tf;
                });
        //等待任务3执行结果
        System.out.println(f3.join());
        System.out.println(System.nanoTime() - start);
    }

    public void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void test() {
        long start = System.nanoTime();
        System.out.println("T1:洗水壶...");
        sleep(1, TimeUnit.SECONDS);

        System.out.println("T1:烧开水...");
        sleep(15, TimeUnit.SECONDS);
        System.out.println("T2:洗茶壶...");
        sleep(1, TimeUnit.SECONDS);

        System.out.println("T2:洗茶杯...");
        sleep(2, TimeUnit.SECONDS);

        System.out.println("T2:拿茶叶...");
        sleep(1, TimeUnit.SECONDS);
        System.out.println("T1:拿到茶叶:");
        System.out.println("T1:泡茶...");

        System.out.println(System.nanoTime() - start);
    }
}
