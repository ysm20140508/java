package com.jxnu.java.DynamicProxy.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 被代理的实体
 * @author shoumiao_yao
 * @date 2017-01-19
 */
public class CglibProxyEvent {
    private final static Logger logger= LoggerFactory.getLogger(CglibProxyEvent.class);
    public void proxied() {
        logger.info("proxyEvent...");
    }
}
