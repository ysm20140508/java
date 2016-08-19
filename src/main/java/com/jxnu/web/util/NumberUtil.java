package com.jxnu.web.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;

/**
 * @author shoumiao_yao
 * @date 2016-08-06
 */
public class NumberUtil {
    private final static Integer length=4;

    public static String SerialNumber(String appName){
        String nowDate= DateFormatUtils.format(new Date(),"yyyyMMddhhmmss");
        nowDate=nowDate+appName+randomNum();
        return nowDate;
    }

    public static String randomNum(){
        StringBuffer randomNum=new StringBuffer();
        for(int index=0;index<length;index++){
            randomNum.append(Integer.valueOf(new Random().nextInt(9)));
        }
        return randomNum.toString();
    }

    public static void main(String[] args) {
        System.out.println(NumberUtil.SerialNumber("clue"));
    }
}
