package com.jxnu.java.basic;

import com.jxnu.java.Class.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author shoumiao_yao
 * @date 2017-02-22
 */
public class FieldType {
    private final static Logger logger = LoggerFactory.getLogger(FieldType.class);
    private int a = 1;
    private Integer ai = 1;
    private int[] as = new int[]{1};
    private String b = "a";
    private String[] bs = new String[]{"a"};
    private Float f = 1.0f;
    private Float[] fs = new Float[]{1.0f};
    private Double d = 1.0;
    private Double[] ds = new Double[]{1.1};
    private List<String> list = new ArrayList<String>();
    private Set<String> set = new HashSet<String>();
    private Map<String, String> map = new HashMap<String, String>();
    private DateUtil dateUtil = new DateUtil();


    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        FieldType fieldTest = new FieldType();
        Field[] fields = FieldType.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object object = field.get(fieldTest);
            //基本类型
            if (field.getType().isPrimitive()) {
                logger.info("primitive...");
                //基本包装类型
            } else if (isWrapClass(object.getClass())) {
                logger.info("wrapClass...");
                //数组类型
            } else if (object.getClass().isArray()) {
                logger.info("array...");
                //collection集合
            } else if (object instanceof Collection) {
                logger.info("collection...");
                //map
            } else if (object instanceof Map) {
                logger.info("map...");
            }
        }
    }

    //判断一个类是否基本包装类型
    public static Boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getDeclaredField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
