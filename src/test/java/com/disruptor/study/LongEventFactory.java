package com.disruptor.study;

import com.lmax.disruptor.EventFactory;

/**
 * @author xuedui.zhao
 * @create 2019-05-15
 */
public class LongEventFactory implements EventFactory {

    @Override
    public Object newInstance() {
        return new LongEvent();
    }

}
