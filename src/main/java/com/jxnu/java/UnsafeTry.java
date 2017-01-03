package com.jxnu.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

/**
 * @author shoumiao_yao
 * @date 2017-01-03
 */
public class UnsafeTry {
    private static final Unsafe THE_UNSAFE;
    private static final Logger logger = LoggerFactory.getLogger(UnsafeTry.class);

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
        //arrayIndexScale方法也是一个本地方法，可以获取数组的转换因子，也就是数组中元素的增量地址
        logger.info(String.valueOf(THE_UNSAFE.arrayIndexScale(Object[].class)));
        //arrayBaseOffset方法是一个本地方法，可以获取数组第一个元素的偏移地址
        logger.info(String.valueOf(THE_UNSAFE.arrayBaseOffset(Object[].class)));
    }
}
