package com.singleton.study;

/**
 * @author xuedui.zhao
 * @create 2019-05-23
 */
public class MutableSingletonComplete {

    private static volatile MutableSingletonComplete INSTANCE;
    private static final Object mutex = new Object();
    private volatile boolean someFlag;
    private volatile int counter;

    // more mutable state on this singleton
    private MutableSingletonComplete(boolean someFlag) {
        this.someFlag = someFlag;
    }

    public static MutableSingletonComplete getInstance() {
        MutableSingletonComplete singleton = INSTANCE;
        if (singleton != null)
            return singleton;
        synchronized (mutex) {
            if (INSTANCE == null)
                INSTANCE = new MutableSingletonComplete(false);
            return INSTANCE;
        }
    }

    public boolean isSomeFlag() {
        return someFlag;
    }

    public void setSomeFlag(boolean someFlag) {
        this.someFlag = someFlag;
    }

    public int getCounter() {
        return counter;
    }

    public synchronized void incrementCounter() {
        counter++;
    }
}
