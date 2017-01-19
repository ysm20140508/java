package com.jxnu.java.DynamicProxy.java;

import java.lang.reflect.Proxy;

/**
 * @author shoumiao_yao
 * @date 2017-01-19
 */
public class JaveProxyTest {
    public static void main(String[] args) {
        JavaSubProxyEvent subProxyEvent = new JavaSubProxyEvent();
        JavaProxyEvent javaProxyEvent = (JavaProxyEvent) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{JavaProxyEvent.class}, new JavaProxy(subProxyEvent));
        javaProxyEvent.proxied();
    }
}
