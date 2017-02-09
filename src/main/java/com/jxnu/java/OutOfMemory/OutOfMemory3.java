package com.jxnu.java.OutOfMemory;

import com.jxnu.java.DynamicProxy.cglib.CglibProxy;
import com.jxnu.java.DynamicProxy.cglib.CglibProxyEvent;
import com.jxnu.java.DynamicProxy.cglib.CglibProxyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方法区内存泄露
 * @author shoumiao_yao
 * @date 2017-02-09
 */
public class OutOfMemory3 {
    private static final Logger logger = LoggerFactory.getLogger(OutOfMemory3.class);

    public static void main(String[] args) {
        int index = 0;
        while (true) {
            index++;
            CglibProxyInterceptor interceptor = new CglibProxyInterceptor();
            CglibProxyEvent proxyEvent = (CglibProxyEvent) CglibProxy.proxy(CglibProxyEvent.class, interceptor);
            logger.info("index:{}",index);
        }
    }

}
