package com.bufferTrigger;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.After;
import org.junit.Test;

import com.github.phantomthief.collection.BufferTrigger;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-20
 * @Description
 */
public class BufferTriggerTest {

    // 这个声明是lazy的，只有第一次执行enqueue时才会创建schedule线程池和容器，所以是可以被安全的在field里声明的
    private BufferTrigger<String> bufferTrigger = BufferTrigger.<String>batchBlocking() //
            .batchSize(4) // 每100个归并一次
            .linger(10, TimeUnit.SECONDS) // 每隔1秒消费一次
            .setConsumerEx(this::doBatchConsumer) // 消费调用函数（会在一个独立线程执行）
            .build();

    private BufferTrigger<String> simpleBufferTrigger = BufferTrigger.<String, Multiset<String>>simple() //
            .setContainerEx(ConcurrentHashMultiset::create, this::add) // 容器方法，入队方法
            .interval(5, TimeUnit.SECONDS) // 每隔1秒消费一次
            .consumer(this::doConsumer) // 消费调用函数（会在一个独立线程执行）
            .build();

    @After
    public void after() {
        bufferTrigger.manuallyDoTrigger(); // 程序结束时把所有积攒的数据一次性消费干净
    }

    void batchConsumer(String photo) {
        bufferTrigger.enqueue(photo); // 这个会调用入队方法，把photo压入定制的容器中
    }

    /**
     * 消费方法
     */
    private void doBatchConsumer(List<String> strList) {
        System.out.println("batch start");
        strList.forEach(System.out::println);
    }

    void consumer(String str) {
        simpleBufferTrigger.enqueue(str); // 这个会调用入队方法，把photo压入定制的容器中
    }


    /**
     * 入队方法
     */
    private int add(Multiset<String> set, String str) {
        set.add(str);
        return 1; // 表示这次操作实际的改动数
    }

    /**
     * 消费方法
     */
    private void doConsumer(Multiset<String> multiset) {
        System.out.println("simple start");
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            System.out.println(entry);
        }
    }

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            batchConsumer("hello word batch " + i);
            consumer("hello word simple " + i);
        }
        LockSupport.parkUntil(this, System.currentTimeMillis() + 12000);
        System.out.println("over");
    }

}
