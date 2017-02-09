package com.jxnu.java.OutOfMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

/**
 * 直接内存内存泄露
 * @author shoumiao_yao
 * @date 2017-02-09
 */
public class OutOfMemory4 {
    private static final Logger logger = LoggerFactory.getLogger(OutOfMemory4.class);
    private static final Unsafe THE_UNSAFE;

    static {
        try {
            final PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    return (Unsafe) theUnsafe.get(null);
                }
            };
            THE_UNSAFE = AccessController.doPrivileged(action);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load unsafe", e);
        }
    }

    public static void main(String[] args) {
        int index = 0;
        while (true) {
            index++;
            logger.info("index:{}", index);
            THE_UNSAFE.allocateMemory(100l);
        }

    }
}
