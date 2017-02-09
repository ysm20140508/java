package com.jxnu.java.OutOfMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 栈内存泄露
 * @author shoumiao_yao
 * @date 2017-02-09
 */
public class OutOfMemory2 {
    private final static Logger logger = LoggerFactory.getLogger(OutOfMemory2.class);
    private final static AtomicLong num=new AtomicLong(0);

    public static void main(String[] args) {
        while (true) {
            sss();
        }
    }

    public static void sss() {
        logger.info("index:{}",num.getAndIncrement());
        sss();
    }
}
