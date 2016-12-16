package com.jxnu.disruptor;


import com.lmax.disruptor.EventHandler;

/**
 * @author shoumiao_yao
 * @date 2016-12-15
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event: " + event);
    }
}
