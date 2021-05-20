package com.juc.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

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

    @Test
    public void test02() {
        get();
        new Thread(() -> {
            lock.lock();
            try {
                if (oddcondition != null) {
                    System.out.println("signal");
                    oddcondition.signal();
                }
            } finally {
                lock.unlock();
            }
        }).start();
    }

    public void get() {
        lock.lock();
        try {
            oddcondition.await(2000, TimeUnit.MILLISECONDS);
            System.out.println("oddcondition_await");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testLockSupport() throws Exception {
        Thread thread = new Thread(() -> {
            System.out.println("Thread start: " + Thread.currentThread().getName());
            Long startTime = System.currentTimeMillis();
            LockSupport.park(); // 阻塞自己
            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println("Thread end: " + Thread.currentThread().getName());
        });

        thread.start();
        thread.setName("A");

        System.out.println("Main thread sleep 3 second: " + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(5);
        LockSupport.unpark(thread); // 唤醒线程 A
    }

    @Test
    public void testLockSupportBlock() throws Exception {
        Thread thread = new Thread(() -> {
            System.out.println("Thread start: " + Thread.currentThread().getName());
            Long startTime = System.currentTimeMillis();
            LockSupport.park(this); // 阻塞自己
            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println("Thread end: " + Thread.currentThread().getName());
        });

        thread.start();
        thread.setName("A");

        System.out.println("Main thread sleep 3 second: " + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(5);
        LockSupport.unpark(thread); // 唤醒线程 A
    }

    @Test
    public void testLockSupportUntil() throws Exception {
        Thread thread = new Thread(() -> {
            System.out.println("Thread start: " + Thread.currentThread().getName());
            Long startTime = System.currentTimeMillis();
            LockSupport.parkUntil(this, System.currentTimeMillis() + 3000); // 阻塞自己
            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println("Thread end: " + Thread.currentThread().getName());
        });

        thread.start();
        thread.setName("A");

        System.out.println("Main thread sleep 3 second: " + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void testLockSupport2() throws Exception {
        Thread thread = new Thread(() -> {
            System.out.println("Thread start: " + Thread.currentThread().getName());
            Long startTime = System.currentTimeMillis();
            LockSupport.park(); // 阻塞自己
            System.out.println("Thread middle: " + Thread.currentThread().getName() + "  " + (System.currentTimeMillis() - startTime));
            LockSupport.park();
            System.out.println("Thread end: " + Thread.currentThread().getName() + "  " + (System.currentTimeMillis() - startTime));
        });

        thread.start();
        thread.setName("A");

        System.out.println("Main thread sleep 3 second: " + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(5);
        LockSupport.unpark(thread); // 唤醒线程 A

        TimeUnit.SECONDS.sleep(5);
        LockSupport.unpark(thread); // 唤醒线程 A
    }

}
