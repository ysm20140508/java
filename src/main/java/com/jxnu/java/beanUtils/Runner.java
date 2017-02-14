package com.jxnu.java.beanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author shoumiao_yao
 * @date 2017-02-14
 */
public class Runner implements Runnable {
    private Date1 date;
    private Date2 date2;

    public Runner(Date1 date, Date2 date2) {
        this.date = date;
        this.date2 = date2;
    }

    public void run() {
        try {
            while (true) {
                org.apache.commons.beanutils.BeanUtils.copyProperties(this.date, this.date2);
                System.out.println(this.date2.getDate());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(this.date2.getDate());
    }
}
