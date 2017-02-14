package com.jxnu.java.OutOfMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author shoumiao_yao
 * @date 2017-02-14
 */
public class OutOfMemory5 {
    private final static Logger logger = LoggerFactory.getLogger(OutOfMemory2.class);
    private final static AtomicLong num = new AtomicLong(0);

    public static void main(String[] args) {
        OutOfMemory5 outOfMemory = new OutOfMemory5();
        outOfMemory.sssByThread();
    }

    public void sssByThread() {
        while (true) {
            new Thread(new Runnable() {
                public void run() {
                    sss();
                }
            }).start();
        }
    }

    public void sss() {
        while (true) {
            logger.info("index:{}", num.getAndIncrement());
        }
    }
}
