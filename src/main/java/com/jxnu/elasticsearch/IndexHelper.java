package com.jxnu.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
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
public class IndexHelper {
    private Logger logger = LoggerFactory.getLogger(IndexHelper.class);
    private Client client;

    public IndexHelper(Client client) {
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
     */
    public String document(String index, String type, String json) {
        IndexRequestBuilder builder = client.prepareIndex(index, type);
        builder.setSource(json);
        ListenableActionFuture<IndexResponse> future = builder.execute();
        IndexResponse indexResponse = future.actionGet(5, TimeUnit.SECONDS);
        if (!future.isDone() || indexResponse == null) {
            return null;
        } else {
            logger.info("document response:{}", indexResponse.toString());
            return indexResponse.getId();
        }
    }

    /**
     * 批量插入document
     *
     * @param index
     * @param type
     * @return
     */
    public boolean batchDocument(String index, String type, List<String> stringList) {
        BulkRequestBuilder builder = this.client.prepareBulk();
        for (String json : stringList) {
            IndexRequestBuilder indexBuild = this.client.prepareIndex(index, type);
            indexBuild.setSource(json);
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

    /**
     * 根据id查找document
     * @param index
     * @param type
     * @param id
     * @return
     */
    public String findDocumentById(String index,String type,String id){
        GetRequest request=new GetRequest();
        request.index(index);
        request.type(type);
        request.id(id);
        ActionFuture<GetResponse> future=client.get(request);
        GetResponse response=future.actionGet(5,TimeUnit.SECONDS);
        if(!future.isDone() || response==null){
            return null;
        }else {
            return response.getSourceAsString();
        }
    }

    /**
     * 批量查找document
     * @param index
     * @param type
     * @param ids
     * @return
     */
    public Map<String,String> batchFind(String index,String type,String... ids){
        BulkRequestBuilder builder=client.prepareBulk();
        for(String id : ids){
            IndexRequest request=new IndexRequest();
            request.index(index);
            request.type(type);
            request.id(id);
            builder.add(request);
        }
        ListenableActionFuture<BulkResponse> future=builder.execute();
        BulkResponse responses=future.actionGet(5,TimeUnit.SECONDS);
        BulkItemResponse[] itemResponses=responses.getItems();
        for(BulkItemResponse itemResponse : itemResponses){
             IndexResponse indexResponse=itemResponse.getResponse();
        }
        return null;
    }

    /**
     * 根据id 删除索引
     * @param index
     * @param type
     * @param id
     * @return
     */
    public Boolean deleteDocumentById(String index,String type,String id){
        DeleteRequest request=new DeleteRequest();
        request.index(index);
        request.type(type);
        request.id(id);
        ActionFuture<DeleteResponse> future=client.delete(request);
        DeleteResponse response=future.actionGet(5,TimeUnit.SECONDS);
        if(!future.isDone() || response !=null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 批量删除索引
     * @param index
     * @param type
     * @param ids
     * @return
     */
    public Boolean batchDeleteDocumentById(String index,String type,String... ids){
        BulkRequestBuilder builder=client.prepareBulk();
        for(String id : ids){
            DeleteRequest request=new DeleteRequest();
            request.index(index);
            request.type(type);
            request.id(id);
            builder.add(request);
        }
        ListenableActionFuture<BulkResponse> future=builder.execute();
        BulkResponse responses=future.actionGet(5,TimeUnit.SECONDS);
        if(!future.isDone() || responses !=null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 更新指定的document
     * @param index
     * @param type
     * @param id
     * @param json
     * @return
     */
    public Boolean update(String index,String type,String id,String json){
        UpdateRequest request=new UpdateRequest();
        request.index(index);
        request.type(type);
        request.id(id);
        request.doc(json);
        ActionFuture<UpdateResponse> future=client.update(request);
        UpdateResponse response=future.actionGet(5,TimeUnit.SECONDS);
        if(!future.isDone() || response !=null){
            return false;
        }else {
            return true;
        }
    }


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cluster.name", "es");
        map.put("client.transport.sniff", "true");
        Client client=ClientFactory.createTransportClient(map, "192.168.179.128", 9300);
        IndexHelper indexHelper = new IndexHelper(client);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "userName");/*
        String id= indexHelper.document("client", "type1", jsonObject.toJSONString());
        indexHelper.batchDocument("client","type2", Collections.singletonList(jsonObject.toJSONString()));*/
        indexHelper.batchDeleteDocumentById("client","type2",
                new String[]{"AVa3y_HvTTXp8FrcVIc0"});
    }
}
