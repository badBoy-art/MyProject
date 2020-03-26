package com.lock.study;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuedui.zhao
 * @create 2019-08-19
 */
public class ReentrantLockStudy {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition oddcondition = lock.newCondition();
    private static Condition evencondition = lock.newCondition();
    private static int count = 0;

    @Test
    public void test01() {
        final Thread main = Thread.currentThread();

        new Thread(() -> {
            for (; ; ) {
                if (count >= 100) {
                    break;
                }
                lock.lock();
                try {
                    if (count % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + "输出" + count++ + "ThreadId：" + Thread.currentThread().getId());
                        oddcondition.signal();
                    } else {
                        evencondition.await();
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                } finally {
                    lock.unlock();
                }
            }
            LockSupport.unpark(main);
        }, "偶数线程").start();


        new Thread(() -> {
            for (; ; ) {
                if (count >= 100) {
                    break;
                }
                lock.lock();
                try {
                    if (count % 2 != 0) {
                        System.out.println(Thread.currentThread().getName() + "输出" + count++ + "ThreadId：" + Thread.currentThread().getId());
                        evencondition.signal();
                    } else {
                        oddcondition.await();
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                } finally {
                    lock.unlock();
                }
            }
            LockSupport.unpark(main);
        }, "奇数线程").start();

        LockSupport.park();
        System.out.println("over");
    }

}
