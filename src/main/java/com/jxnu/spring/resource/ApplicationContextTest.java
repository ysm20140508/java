package com.jxnu.spring.resource;

import com.jxnu.spring.aop.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shoumiao_yao
 * @date 2016-08-03
 */
public class ApplicationContextTest {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationContextTest.class);

    public static void main(String[] args) {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring.xml");
        Dao dao=(Dao) ctx.getBean("dao");
        dao.testAopAnnotation();
    }
}
