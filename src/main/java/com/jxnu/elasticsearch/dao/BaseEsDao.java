package com.jxnu.elasticsearch.dao;

import com.jxnu.elasticsearch.helper.SearchHelper;

import java.util.List;

/**
 * @author shoumiao_yao
 * @date 2016-09-27
 */
public interface BaseEsDao<T> {
    /**
     * 保存
     *
     * @param t
     * @return
     */
    public String save(T t);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    public T findById(String id);

    /**
     * 删除对象
     *
     * @param id
     * @return
     */
    public Boolean delete(String id);

    /**
     * 更新对象
     *
     * @param t
     * @return
     */
    public Boolean update(T t);

    /**
     * 查询
     *
     * @return
     */
    public List<T> query(SearchHelper searchHelper);

}
