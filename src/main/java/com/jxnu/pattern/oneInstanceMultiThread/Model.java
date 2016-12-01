package com.jxnu.pattern.oneInstanceMultiThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shoumiao_yao
 * @date 2016-12-01
 */
public class Model {
    private Logger logger = LoggerFactory.getLogger(Model.class);
    private String name;

    public Model(String name) {
        this.name = name;
    }

    public void name() {
        logger.info("thread id:{} name:{}", Thread.currentThread().getId(), name);
        logger.info("thread id:{} name:{}", Thread.currentThread().getName(), name);
    }
}
