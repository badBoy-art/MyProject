package com.syncandvolatile.study;

/**
 * @author xuedui.zhao
 * @create 2019-05-28
 */
public class SynchronizedTest {

    public synchronized void test1() {

    }

    public void test2() {
        synchronized (this) {

        }
    }
}
