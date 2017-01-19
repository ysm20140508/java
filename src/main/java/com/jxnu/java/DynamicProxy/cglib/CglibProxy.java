package com.jxnu.java.DynamicProxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author shoumiao_yao
 * @date 2017-01-19
 */
public class CglibProxy {

    public static Object proxy(Class<CglibProxyEvent> eventClass, MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(eventClass);
        enhancer.setCallback(interceptor);
        return enhancer.create();
    }
}
