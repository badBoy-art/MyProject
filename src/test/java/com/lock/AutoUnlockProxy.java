package com.lock;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-12-09
 * @Description
 */
public class AutoUnlockProxy implements Closeable {

    private Lock lock;

    public AutoUnlockProxy(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void close() throws IOException {
        lock.unlock();
        System.out.println("释放锁");
    }

    public void lock() {
        lock.lock();
    }

    public void tryLock(long time, TimeUnit unit) throws InterruptedException {
        lock.tryLock(time, unit);
    }

    public static void main(String[] args) {

        try (AutoUnlockProxy autoUnlockProxy = new AutoUnlockProxy(new ReentrantLock())) {
            autoUnlockProxy.lock();
            System.out.println("加锁了");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
