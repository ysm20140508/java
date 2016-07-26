package com.jxnu.spring.test;

import com.jxnu.spring.aop.Dao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author shoumiao_yao
 * @date 2016-07-22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class JunitTest {
    @Resource
    private Dao dao;

    @Test
    public void test() {
        dao.test("123", "123");
    }
}
