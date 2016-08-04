package com.jxnu.spring.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author shoumiao_yao
 * @date 2016-08-03
 */
public class ResourceLoaderTest {
    private final static Logger logger = LoggerFactory.getLogger(ResourceLoaderTest.class);

    public static void main(String[] args) throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:spring.xml");
        logger.info("fileName:{}", resource.getFilename());
        logger.info("URL:{}", resource.getURL());
        logger.info("description:{}", resource.getDescription());
        logger.info("URI:{}", resource.getURI());
    }
}
