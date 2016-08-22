package com.jxnu.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author shoumiao_yao
 * @date 2016-08-22
 */
public class IndexDao {
    private Logger logger = LoggerFactory.getLogger(IndexDao.class);
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * 创建类型
     *
     * @param index
     * @param type
     * @return
     */
    public boolean type(String index, String type) {
        IndexRequest request = new IndexRequest(index, type);
        ActionFuture<IndexResponse> response = client.index(request);
        IndexResponse indexResponse = response.actionGet(5, TimeUnit.SECONDS);
        if (!response.isDone() || indexResponse == null) {
            return false;
        } else {
            logger.info("type response:{}", indexResponse.toString());
            return true;
        }
    }

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public boolean index(String index) {
        IndexRequest request = new IndexRequest(index);
        ActionFuture<IndexResponse> response = client.index(request);
        IndexResponse indexResponse = response.actionGet(5, TimeUnit.SECONDS);
        if (!response.isDone() || indexResponse == null) {
            return false;
        } else {
            logger.info("index response:{}", indexResponse.toString());
            return true;
        }
    }

    /**
     * 插入document
     *
     * @param index
     * @param type
     * @param jsonObject
     */
    public boolean document(String index, String type, JSONObject jsonObject) {
        IndexRequestBuilder builder = client.prepareIndex(index, type);
        builder.setSource(jsonObject.toJSONString());
        ListenableActionFuture<IndexResponse> future = builder.execute();
        IndexResponse indexResponse = future.actionGet(5, TimeUnit.SECONDS);
        if (!future.isDone() || indexResponse == null) {
            return false;
        } else {
            logger.info("document response:{}", indexResponse.toString());
            return true;
        }
    }

    /**
     * 批量插入document
     *
     * @param index
     * @param type
     * @param jsonObjectList
     * @return
     */
    public boolean batchDocument(String index, String type, List<JSONObject> jsonObjectList) {
        BulkRequestBuilder builder = this.client.prepareBulk();
        for (JSONObject jsonObject : jsonObjectList) {
            IndexRequestBuilder indexBuild = this.client.prepareIndex(index, type);
            indexBuild.setSource(jsonObject.toJSONString());
            builder.add(indexBuild);
        }
        ListenableActionFuture<BulkResponse> future = builder.execute();
        BulkResponse bulkResponses = future.actionGet(5, TimeUnit.SECONDS);
        if (!future.isDone() || bulkResponses == null) {
            return false;
        } else {
            logger.info("bulk index response:{}", bulkResponses.toString());
            return true;
        }
    }


    public static void main(String[] args) {
        IndexDao indexDao = new IndexDao();
        Map<String, String> map = new HashMap<String, String>();
        map.put("cluster.name", "es");
        map.put("client.transport.sniff", "true");
        indexDao.setClient(ClientFactory.createTransportClient(map, "192.168.179.128", 9300));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "userName");
        indexDao.document("client", "type1", jsonObject);
        indexDao.batchDocument("client","type2", Collections.singletonList(jsonObject));
    }
}
