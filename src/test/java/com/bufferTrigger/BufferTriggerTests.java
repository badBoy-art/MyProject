package com.bufferTrigger;


import static com.alibaba.csp.sentinel.util.TimeUtil.currentTimeMillis;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.locks.LockSupport;

import org.junit.Before;
import org.junit.Test;

import com.github.phantomthief.collection.BufferTrigger;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;

/**
 * @author yourname <yourname>
 * Created on 2020-01-03
 */
public class BufferTriggerTests {

    @Test
    public void test() {
        for (int i = 1; i < 20; i++) {
            buffer.enqueue("test" + i);
            //sleepUninterruptibly(1,SECONDS);
        }
        LockSupport.parkUntil(this, currentTimeMillis() + 3000);
        System.out.println("over");
    }

    private BufferTrigger<String> buffer;

    @Before
    public void init() {
        buffer = BufferTrigger.<String, Multiset<String>>simple() //
                // 触发策略，每秒执行一次
                .interval(2, SECONDS)
                // 容器的工场，以及入队方法
                .setContainer(ConcurrentHashMultiset::create, Multiset::add)
                // 消费方法
                .consumer(this::update) //
                .build();
        // 程序结束时应该把没有刷入的强制同步刷入
        //TermHelper.addTerm(buffer::manuallyDoTrigger); // TermHelper.addTerm
    }

    void update(Multiset<String> multiset) {
        multiset.entrySet().forEach(entry -> System.out.println(entry));
    }
}
