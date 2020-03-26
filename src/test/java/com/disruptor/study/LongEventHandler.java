package com.disruptor.study;


import com.lmax.disruptor.EventHandler;

/**
 * 时间处理器
 *
 * @author xuedui.zhao
 * @create 2019-05-15
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println(longEvent.getValue());
    }

}
