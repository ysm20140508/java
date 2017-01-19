package com.jxnu.java.DynamicProxy.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shoumiao_yao
 * @date 2017-01-19
 */
public class JavaSubProxyEvent implements JavaProxyEvent {
    private final static Logger logger= LoggerFactory.getLogger(JavaSubProxyEvent.class);

    public void proxied() {
        logger.info("java proxied...");
    }
}
