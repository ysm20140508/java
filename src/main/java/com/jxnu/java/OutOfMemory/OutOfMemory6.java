package com.jxnu.java.OutOfMemory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shoumiao_yao
 * @date 2017-02-14
 */
public class OutOfMemory6 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
