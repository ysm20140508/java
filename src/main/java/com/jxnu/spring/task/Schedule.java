package com.jxnu.spring.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author shoumiao_yao
 * @date 2016-07-22
 */
@Component
public class Schedule {
    private final static Logger logger = LoggerFactory.getLogger(Schedule.class);
    
    @Scheduled(fixedRate = 1000)
    public void doSomething() {
        logger.info("scheduled...");
    }
}
