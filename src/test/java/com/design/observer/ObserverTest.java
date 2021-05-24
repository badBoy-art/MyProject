package com.design.observer;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description 观察者模式：核心的部分，那就是一定有一个地方存放了所有的观察者，然后在事件发生的时候，遍历观察者，调用它们的回调函数
 */
public class ObserverTest {

    public static void main(String[] args) {
        // 先定义一个主题
        Subject subject = new Subject();
        // 定义观察者
        new BinaryObserver(subject);
        new HexaObserver(subject);

        // 模拟数据变更，这个时候，观察者们的 update 方法将会被调用
        subject.setState(11);
    }

}
