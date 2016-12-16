package com.jxnu.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author shoumiao_yao
 * @date 2016-12-15
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    public LongEvent newInstance() {
        return new LongEvent();
    }
}
