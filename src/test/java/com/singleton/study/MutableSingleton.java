package com.singleton.study;

/**
 * 双重加锁
 * 对 `INSTANCE` 字段使用 `volatile` 关键字，应用一次性安全发布；
 * <p>
 * 对单例属性使用 `volatile` 关键字，保持可见性；
 * <p>
 * 如果修改状态不是原子操作，即使该字段声明为 `volatile`，也需要使用 `synchronized` 关键字，使用互斥锁；
 * <p>
 * 如果单例中的 `volatile` 属性是对象引用，要么该对象也是线程安全的实现，要么对象本身不可变。这样实现会更加完善。
 *
 * @author xuedui.zhao
 * @create 2019-05-23
 */
public class MutableSingleton {

    private static volatile MutableSingleton INSTANCE;
    private static final Object mutex = new Object();
    private volatile boolean someFlag;

    // 这个单例包含更多可变状态
    private MutableSingleton(boolean someFlag) {
        this.someFlag = someFlag;
    }

    public static MutableSingleton getInstance() {
        MutableSingleton singleton = INSTANCE;
        if (singleton != null) {
            return singleton;
        }
        synchronized (mutex) {
            if (INSTANCE == null) {
                INSTANCE = new MutableSingleton(false);
            }
            return INSTANCE;
        }

    }

    public boolean isSomeFlag() {
        return someFlag;
    }

    public void setSomeFlag(boolean someFlag) {
        this.someFlag = someFlag;
    }

}

