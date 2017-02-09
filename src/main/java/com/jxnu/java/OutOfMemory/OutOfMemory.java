package com.jxnu.java.OutOfMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  堆内存泄露
 * @author shoumiao_yao
 * @date 2017-02-09
 */
public class OutOfMemory {
    private static final Logger logger= LoggerFactory.getLogger(OutOfMemory.class);

    public static void main(String[] args) throws InterruptedException {
        List<int[]> b=new ArrayList<int[]>();
        int index=0;
        while (true){
            Thread.sleep(10);
            int[] a=new int[10000];
            index++;
            b.add(a);
            logger.info("index:{}",index);
        }
    }
}
