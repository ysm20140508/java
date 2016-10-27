package com.jxnu.elasticsearch.helper;

import com.jxnu.elasticsearch.client.EsClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * elasticSearch 真正执行的地方
 *
 * @author shoumiao_yao
 * @date 2016-09-28
 */
public class ElasticSearchHelper {
    private Logger logger = LoggerFactory.getLogger(ElasticSearchHelper.class);
    private Integer timeOut = 5;
    private String index;
    private String type;
    private Client client;
    private IndexHelper indexHelper;

    /**
     * 构造器
     *
     * @param index
     * @param type
     * @param esClient
     */
    public ElasticSearchHelper(String index, String type, EsClient esClient) {
        this.index = index;
        this.type = type;
        this.client = esClient.getClient();
        this.indexHelper = new IndexHelper.Build().index(this.index).type(this.type).build();
    }

    /**
     * 保存文档
     *
     * @param source
     * @return
     */
    public String save(String source) {
        IndexRequest request = indexHelper.document(source);
        try {
            IndexResponse response = client.index(request).actionGet(this.timeOut, TimeUnit.SECONDS);
            if (response == null) return "";
            String id = response.getId();
            if (StringUtils.isNotBlank(id)) return id;
        } catch (Exception e) {
            logger.error("save error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            return "";
        }
    }

    /**
     * 根据id查找文档
     *
     * @param id
     * @return
     */
    public String findById(String id) {
        GetRequest request = indexHelper.findDocumentById(id);
        try {
            GetResponse response = client.get(request).actionGet(this.timeOut, TimeUnit.SECONDS);
            if (response == null) return "";
            String source = response.getSourceAsString();
            if (StringUtils.isNotBlank(source)) return source;
        } catch (Exception e) {
            logger.error("findById error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            return "";
        }
    }

    /**
     * 根据id删除文档
     *
     * @param id
     * @return
     */
    public Boolean delete(String id) {
        DeleteRequest request = indexHelper.deleteDocumentById(id);
        try {
            DeleteResponse response = client.delete(request).actionGet(this.timeOut, TimeUnit.SECONDS);
            if (response == null) return false;
            String oldId = response.getId();
            if (StringUtils.isNotBlank(oldId)) return true;
        } catch (Exception e) {
            logger.error("delete error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            return false;
        }
    }

    /**
     * 根据id更新文档
     *
     * @param id
     * @param source
     * @return
     */
    public Boolean update(String id, String source) {
        UpdateRequest request = indexHelper.update(id, source);
        try {
            UpdateResponse response = client.update(request).actionGet(this.timeOut, TimeUnit.SECONDS);
            if (response == null) return false;
            String oldId = response.getId();
            if (StringUtils.isNotBlank(oldId)) return true;
        } catch (Exception e) {
            logger.error("update error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            return false;
        }
    }

    /**
     * 查询
     *
     * @return
     */
    public List<String> query(SearchHelper searchHelper) {
        List<String> jsons = new ArrayList<String>();
        try {
            SearchRequestBuilder builder = client.prepareSearch().setIndices(this.index).setTypes(this.type);
            builder.setFrom(searchHelper.start());
            builder.setTerminateAfter(searchHelper.limit());
            builder.internalBuilder(searchHelper.build());
            SearchResponse response = builder.execute().actionGet(this.timeOut, TimeUnit.SECONDS);
            if (response == null) return jsons;
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                String source = hit.getSourceAsString();
                jsons.add(source);
            }
        } catch (Exception e) {
            logger.error("query error:{}",ExceptionUtils.getStackTrace(e));
        }
        return jsons;
    }

}
