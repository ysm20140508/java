package com.jxnu.elasticsearch.dao;

import com.alibaba.fastjson.JSON;
import com.jxnu.elasticsearch.helper.SearchHelper;
import com.jxnu.elasticsearch.model.BaseModel;
import com.jxnu.elasticsearch.annotation.Index;
import com.jxnu.elasticsearch.client.EsClient;
import com.jxnu.elasticsearch.helper.ElasticSearchHelper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shoumiao_yao
 * @date 2016-09-27
 */
public class BaseEsDaoImpl<T extends BaseModel> implements BaseEsDao<T> {
    private Class entityClass;
    private String type;
    private String index;
    private ElasticSearchHelper helper;
    @Resource(name = "transportClient")
    private EsClient esClient;

    public SearchHelper SearchHelper() {
        return new SearchHelper();
    }

    @PostConstruct
    public void init() {
        initClass();
        this.helper = new ElasticSearchHelper(this.index, this.type, esClient);
    }

    private void initClass() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (this.entityClass.isAnnotationPresent(Index.class)) {
            Index index2 = (Index) this.entityClass.getAnnotation(Index.class);
            if (index2 == null) return;
            this.index = index2.index();
            this.type = index2.type();
        }
    }


    public String save(T t) {
        String source = JSON.toJSONString(t);
        String id = helper.save(source);
        return id;
    }


    public T findById(String id) {
        String soource = helper.findById(id);
        T t = (T) JSON.parseObject(soource, entityClass);
        return t;
    }


    public Boolean delete(String id) {
        return helper.delete(id);
    }


    public Boolean update(T t) {
        BaseModel model = (BaseModel) t;
        return helper.update(model.getId(), JSON.toJSONString(t));
    }

    public List<T> query(SearchHelper searchHelper) {
        List<T> t = new ArrayList<T>();
        List<String> jsonS = helper.query(searchHelper);
        for (String json : jsonS) {
            t.add((T) JSON.parseObject(json, entityClass));
        }
        return t;
    }
}
