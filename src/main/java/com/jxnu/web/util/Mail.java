package com.jxnu.web.util;

import org.apache.commons.mail.SimpleEmail;

import javax.mail.internet.InternetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shoumiao_yao
 * @date 2016-10-09
 */
public class Mail {
    public static void main(String[] args) throws Exception {
        //发送简单邮件
        SimpleEmail email = new SimpleEmail();

        email.setHostName("smtp.qq.com");
        email.setDebug(true);
        email.setSSLOnConnect(true);

        //需要邮件发送服务器验证,用户名/密码
        email.setAuthentication("1247793952@qq.com", "**");
        email.setFrom("1247793952@qq.com");
        email.addTo("15889326057@163.com");

        //设置主题的字符集为UTF-8
        email.setSubject("放假通知");
        email.buildMimeMessage();
        email.getMimeMessage().addHeader("Content-Type","text/plain; charset=utf-8");
        email.getMimeMessage().setText("放假通知");
        email.sendMimeMessage();
    }
}
