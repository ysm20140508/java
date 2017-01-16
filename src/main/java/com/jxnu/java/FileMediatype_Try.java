package com.jxnu.java;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.FileNameMap;
import java.net.URLConnection;

/**
 * 根据文件名称获取mediaType
 * @author shoumiao_yao
 * @date 2017-01-12
 */
public class FileMediatype_Try {
    private final static Logger logger = LoggerFactory.getLogger(FileMediatype_Try.class);

    public static void main(String[] args) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        logger.info("log mediaType:{}", fileNameMap.getContentTypeFor("2.png"));
    }
}
