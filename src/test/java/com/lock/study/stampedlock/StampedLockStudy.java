package com.lock.study.stampedlock;

import java.util.concurrent.locks.StampedLock;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-08
 * @Description
 */
public class StampedLockStudy {

    @Test
    public void test() {
        final StampedLock sl = new StampedLock();
        // 获取/释放悲观读锁示意代码
        long stamp = sl.readLock();
        try {
            System.out.println(stamp);
        } finally {
            sl.unlockRead(stamp);
        }
        // 获取/释放写锁示意代码
        stamp = sl.writeLock();
        try {
            System.out.println(stamp);
        } finally {
            sl.unlockWrite(stamp);
        }
    }

}
