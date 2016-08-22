package com.jxnu.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shoumiao_yao
 * @date 2016-08-19
 */
public class Elasticsearch {
    public static void main(String[] args) throws IOException {
        RestClient restClient=RestClient.builder(new HttpHost("192.168.179.128", 9200)).build();
        Header[] headers=new Header[]{new BasicHeader("Content-Type","application/json")};
        JSONObject object=new JSONObject();
        object.put("name","elasticsearch");
        object.put("userName","kingdee");
        HttpEntity httpEntity=new StringEntity(object.toJSONString(),"utf-8");
        Map<String,String> map=new HashMap<String,String>();
        Response response=restClient.performRequest("post","/elasticsearch/type1/1",map,httpEntity,headers);
        response.getEntity();

    }
}
