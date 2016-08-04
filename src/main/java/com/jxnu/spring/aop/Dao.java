package com.jxnu.spring.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author shoumiao_yao
 * @date 2016-07-21
 */

@Component
public class Dao {
    private Logger logger = LoggerFactory.getLogger(Dao.class);


    @Async
    public void test(String name, String test) {
        logger.info("test...");
    }


    @AopAnnotation
    public void testAopAnnotation(){
        logger.info("test aop annotation....");
    }
}
