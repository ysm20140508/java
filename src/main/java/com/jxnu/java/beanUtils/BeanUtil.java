package com.jxnu.java.beanUtils;


import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * common的BeanUtils 如果原始date为空 导致复制属性失败
 * @author shoumiao_yao
 * @date 2017-02-13
 */
public class BeanUtil {
    static {
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Date1 date = new Date1(null);
        Date2 date2 = new Date2(new Date());
        BeanUtils.copyProperties(date, date2);
        System.out.println(date2.getDate());
        Thread thread = new Thread(new Runner(date, date2));
        Thread thread2 = new Thread(new Runner(date, date2));
        thread.start();
        thread2.start();
    }
}


