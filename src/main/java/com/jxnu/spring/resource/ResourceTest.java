package com.jxnu.spring.resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author shoumiao_yao
 * @date 2016-08-03
 */
public class ResourceTest {
    private final static Logger logger= LoggerFactory.getLogger(ResourceTest.class);

    public static void main(String[] args) throws IOException {
        ClassPathResource resource=new ClassPathResource("spring.xml");
        logger.info("fileName:{}",resource.getFilename());
        logger.info("URL:{}",resource.getURL());
        logger.info("path:{}",resource.getPath());
        logger.info("description:{}",resource.getDescription());
        logger.info("URI:{}",resource.getURI());
    }
}
