package com.jxnu.disruptor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shoumiao_yao
 * @date 2016-12-15
 */
public class DisruptorThreadFactory implements ThreadFactory {
    private final static AtomicInteger num=new AtomicInteger(0);
    private String prfix;

    public DisruptorThreadFactory(String prfix) {
        this.prfix = prfix;
    }

    public Thread newThread(Runnable r) {
        Thread thread=new Thread(r);
        thread.setDaemon(false);
        thread.setName(prfix+"-"+num.getAndIncrement());
        return thread;
    }
}
