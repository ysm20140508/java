package com.jxnu.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author shoumiao_yao
 * @date 2016-07-21
 */
@Component
@Aspect
public class AopTest {
    private final static Logger logger = LoggerFactory.getLogger(AopTest.class);

    @Around("execution(* com.jxnu.spring.aop.*.*(..))")
    public void testPoincutBy(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        logger.info("Elasticsearch testPointcut...");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?>[] types = method.getParameterTypes();
        for (Class clazz : types) {
            System.out.println(clazz.getSimpleName());
        }
        joinPoint.proceed(args);
        return;
    }


    @Around("@annotation(com.jxnu.spring.aop.AopAnnotation)")
    public void TestAopAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("java 注解 annotation");
        Object[] args = joinPoint.getArgs();
        joinPoint.proceed(args);
        return;
    }


    @Before("execution(* com.jxnu.spring.aop.*.*(..))")
    public void TestBefore() {
        logger.info("Elasticsearch before...");
    }

}

