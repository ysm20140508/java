package com.jxnu.java.DynamicProxy.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author shoumiao_yao
 * @date 2017-01-19
 */
public class JavaProxy implements InvocationHandler {
    private final static Logger logger= LoggerFactory.getLogger(JavaProxy.class);
    private Object proxied;

    public JavaProxy(Object proxied) {
        this.proxied = proxied;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("java proxy start...");
        return method.invoke(this.proxied, args);
    }
}
