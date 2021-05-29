package com.lambda.study;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-28
 * @Description lambda死锁
 */
public class LambdaDeadLock {

    public static void main(String[] args) {
        System.out.println("startTest");
        // 起一个线程T1，初始化A
        new Thread(() -> new A()).start();

        // 停顿下，确保T1初始化A类时的“将lamdba传递出去”步骤可以执行完成
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        // 和A类中的key相同，确保可以掉到同样的lamdba表达式
        System.out.println(new Container().get("key"));
        System.out.println("finishTest");
    }

}

class Container {
    private static Map<String, Supplier<String>> supplierMap = new ConcurrentHashMap<>();

    public String get(String key) {
        synchronized (supplierMap) { // 在获取时加锁
            return supplierMap.get(key).get();
        }
    }

    public static Container addSupplier(String key, Supplier<String> supplier) {
        // 将可能的lamdba放入static map，确保其它线程（类可以调用到）
        supplierMap.put(key, supplier);

        // 停顿下，确保有操作可能在：“将lamdba传递出去” 和 “完成类整个初始化” 间调用
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }

        synchronized (supplierMap) { // 和get函数采用同一个锁
            return new Container();
        }
    }
}

class A {
    private static final Container CT = Container.addSupplier("key",
            () -> "value"); // 传入一个lamdba表达式
}