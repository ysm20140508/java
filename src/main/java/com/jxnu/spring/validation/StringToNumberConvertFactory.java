package com.jxnu.spring.validation;


/**
 * @author shoumiao_yao
 * @date 2016-08-05
 */
public class StringToNumberConvertFactory implements ConvertFactory<String, Number> {

    public <T extends Number> Converter<String, T> getInstance(Class<T> clazz) {
        return new StringToNumberConverter<T>(clazz);
    }

    private final static class StringToNumberConverter<T extends Number> implements Converter<String, T> {
        private Class<T> targetClass;

        public StringToNumberConverter(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        public T converter(String s) {
            if (s == null || s.length() == 0) {
                return null;
            }
            if (targetClass==Float.class || targetClass==float.class) {
                return (T) Float.valueOf(s);
            } else if (targetClass==Byte.class || targetClass==byte.class) {
                return (T) Byte.valueOf(s);
            } else if (targetClass==Double.class || targetClass==double.class) {
                return (T) Double.valueOf(s);
            }
            return null;
        }
    }
}
