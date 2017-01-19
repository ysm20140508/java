package com.jxnu.java.DynamicProxy.cglib;

/**
 * @author shoumiao_yao
 * @date 2017-01-19
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        CglibProxyInterceptor interceptor = new CglibProxyInterceptor();
        CglibProxyEvent proxyEvent = (CglibProxyEvent) CglibProxy.proxy(CglibProxyEvent.class, interceptor);
        proxyEvent.proxied();
    }
}
