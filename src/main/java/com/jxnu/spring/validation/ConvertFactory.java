package com.jxnu.spring.validation;


/**
 * @author shoumiao_yao
 * @date 2016-08-04
 */
public interface ConvertFactory<S, R> {

    <T extends R> Converter<S, T> getInstance(Class<T> clazz);
}
