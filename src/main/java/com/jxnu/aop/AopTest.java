package com.jxnu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author shoumiao_yao
 * @date 2016-07-21
 */
@Component
@Aspect
public class AopTest {

    @Around("execution(* com.jxnu.aop.*.*(..))")
    public void testPoincutBy(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        System.out.println("test testPointcut。。。");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?>[] types = method.getParameterTypes();
        for (Class clazz : types) {
            System.out.println(clazz.getSimpleName());
        }
        joinPoint.proceed(args);
        return;
    }

    @Before("execution(* com.jxnu.aop.*.*(..))")
    public void TestBefore() {
        System.out.println("test before。。。");
    }

}

