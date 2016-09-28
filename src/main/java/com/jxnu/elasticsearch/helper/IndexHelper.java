package com.jxnu.elasticsearch.helper;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * index type 操作帮助类
 *
 * @author shoumiao_yao
 * @date 2016-08-22
 */
public class IndexHelper {
    private Logger logger = LoggerFactory.getLogger(IndexHelper.class);
    private String index;
    private String type;

    public IndexHelper(IndexHelper.Build build) {
        this.index = build.index;
        this.type = build.type;
    }

    /**
     * 创建类型
     *
     * @return
     */
    public IndexRequest type() {
        IndexRequest request = Requests.indexRequest(this.index).type(this.type);
        return request;
    }

    /**
     * 创建索引
     *
     * @return
     */
    public IndexRequest index() {
        IndexRequest request = Requests.indexRequest(this.index);
        return request;
    }

    /**
     * 插入document
     *
     * @param json
     * @return
     */
    public IndexRequest document(String json) {
        IndexRequest request = Requests.indexRequest(this.index).type(this.type).source(json);
        return request;
    }

    /**
     * 批量插入document
     *
     * @param stringList
     * @return
     */
    public IndexRequest batchDocument(List<String> stringList) {
        IndexRequest request = Requests.indexRequest(this.index).type(this.type).source(stringList);
        return request;
    }

    /**
     * 根据id查找document
     *
     * @param id
     * @return
     */
    public GetRequest findDocumentById(String id) {
        GetRequest request = Requests.getRequest(this.index).type(this.type).id(id);
        return request;
    }


    /**
     * 根据id 删除索引
     *
     * @param id
     * @return
     */
    public DeleteRequest deleteDocumentById(String id) {
        DeleteRequest request = Requests.deleteRequest(this.index).type(this.type).id(id);
        return request;
    }


    /**
     * 更新指定的document
     *
     * @param id
     * @param json
     * @return
     */
    public UpdateRequest update(String id, String json) {
        UpdateRequest request = new UpdateRequest(this.index, this.type, id).doc(json);
        return request;
    }

    /**
     * 参数设置
     */
    public static class Build {
        private String index;
        private String type;

        public Build index(String index) {
            this.index = index;
            return this;
        }

        public Build type(String type) {
            this.type = type;
            return this;
        }

        public IndexHelper build() {
            return new IndexHelper(this);
        }
    }
}
