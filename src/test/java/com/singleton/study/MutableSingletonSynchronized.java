package com.singleton.study;

/**
 * @author xuedui.zhao
 * @create 2019-05-23
 */
public class MutableSingletonSynchronized {

    private static MutableSingletonSynchronized INSTANCE;
    private static final Object mutex = new Object();
    private boolean someFlag;

    // 这个单例包含更多可变状态
    private MutableSingletonSynchronized(boolean someFlag) {
        this.someFlag = someFlag;
    }

    public static MutableSingletonSynchronized getInstance() {
        MutableSingletonSynchronized singleton = INSTANCE;
        if (singleton != null) {
            return singleton;
        }
        synchronized (mutex) {
            if (INSTANCE == null) {
                INSTANCE = new MutableSingletonSynchronized(false);
            }
            return INSTANCE;
        }
    }

    public synchronized boolean isSomeFlag() {
        return someFlag;
    }

    public synchronized void setSomeFlag(boolean someFlag) {
        this.someFlag = someFlag;
    }
}
