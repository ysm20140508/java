package com.jxnu.pattern.oneInstanceMultiThread;

import java.util.concurrent.Executors;

/**
 * 每个线程有自己独立的方法栈,在调同一个对象实例时,执行到哪会记录到程序计数器,
 * @author shoumiao_yao
 * @date 2016-12-01
 */
public class ModelTest {
    public final static Model model = new Model("TestOneInstance");

    public static void main(String[] args) {
        for (int index = 0; index < 1000; index++) {
            Thread thread=new Thread(new testThread(model));
            Executors.newCachedThreadPool().submit(thread);
        }
    }
}

class testThread implements Runnable{
    private Model model;
    public testThread(Model model) {
        this.model = model;
    }

    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.model.name();
    }
}
