package com.jxnu.java.DynamicProxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author shoumiao_yao
 * @date 2017-01-19
 */
public class CglibProxyInterceptor implements MethodInterceptor {
    private final static Logger logger= LoggerFactory.getLogger(CglibProxyInterceptor.class);

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        logger.info(method.getName()+":proxying....");
        return methodProxy.invokeSuper(o, objects);
    }
}
