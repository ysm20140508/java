package com.jxnu.http_request_client.httpClient;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author shoumiao_yao
 * @date 2016-12-30
 */
public class HttpClientUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private final static CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * get
     *
     * @param url 请求路径
     * @return
     * @throws IOException
     */
    public static HttpResponse get(String url) throws IOException {
        if (StringUtils.isBlank(url)) return null;
        //设置请求参数
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        return response;
    }


    /**
     * post 表单
     *
     * @param params 表单内容
     * @param heads  请求头
     * @param url    请求路径
     * @return
     * @throws IOException
     */
    public static HttpResponse post(Map<String, String> params, Map<String, String> heads, String url) throws IOException {
        if (CollectionUtils.isEmpty(params) || StringUtils.isBlank(url)) return null;
        //设置请求参数
        HttpPost httpPost = new HttpPost(url);
        //设置表单内容
        Set<String> keys = params.keySet();
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        for (String key : keys) {
            String value = params.get(key);
            BasicNameValuePair pair = new BasicNameValuePair(key, value);
            list.add(pair);
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
        httpPost.setEntity(entity);
        //设置请求头部
        if (!CollectionUtils.isEmpty(heads)) {
            Set<String> headKeys = heads.keySet();
            for (String key : headKeys) {
                String value = heads.get(key);
                httpPost.addHeader(key, value);
            }
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }


    /**
     * post json
     *
     * @param params json的请求参数
     * @param heads  请求头
     * @param url    请求路径
     * @return
     * @throws IOException
     */
    public static HttpResponse postJson(Map<String, String> params, Map<String, String> heads, String url) throws IOException {
        if (CollectionUtils.isEmpty(params) || StringUtils.isBlank(url)) return null;
        //设置json
        String json = JSONObject.toJSONString(params);
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        //设置请求头部
        if (!CollectionUtils.isEmpty(heads)) {
            Set<String> headKeys = heads.keySet();
            for (String key : headKeys) {
                String value = heads.get(key);
                httpPost.addHeader(key, value);
            }
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    /**
     * post json
     *
     * @param json 请求json body
     * @param url  请求路径
     * @return
     */
    public static HttpResponse postJson(String json, String url) throws IOException {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(url)) return null;
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    /**
     * 文件上传
     *
     * @param url         请求路径
     * @param params      请求参数
     * @param heads       请求头
     * @return
     * @throws IOException
     */
    public static HttpResponse postFile(String url, Map<String, Object> params, Map<String, String> heads) throws IOException {
        if (CollectionUtils.isEmpty(params) || StringUtils.isBlank(url)) return null;
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mEntityBuilder.setCharset(Charset.defaultCharset());
        //设置请求体
        Set<String> keys = params.keySet();
        for (String key : keys) {
            Object value = params.get(key);
            if (value instanceof File) {
                File file = (File) value;
                FileBody fileBody = new FileBody(file);
                mEntityBuilder.addPart(key, fileBody);
            } else {
                mEntityBuilder.addTextBody(key, String.valueOf(value));
            }
        }
        //设置请求头部
        if (!CollectionUtils.isEmpty(heads)) {
            Set<String> headKeys = heads.keySet();
            for (String headKey : headKeys) {
                String value = heads.get(headKey);
                httpPost.addHeader(headKey, value);
            }
        }
        httpPost.setEntity(mEntityBuilder.build());
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    public static String parse(CloseableHttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        String result = "";
        if (statusCode != 200) {
            return result;
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        }
        response.close();
        return result;
    }


    public static void main(String[] args) throws IOException {
        String host = "localhost:8080";
        //登录系统 获取cookie (get 请求)
        String testLoginUrl = "##";
        HttpResponse response = HttpClientUtil.get(testLoginUrl);
        Header header = response.getLastHeader("Set-Cookie");
        String cookie = header.getValue();
        //(post 表单)
        String formUrl = "##";
        Map<String, String> formParams = new HashMap<String, String>();
        formParams.put("sn", "sn");
        formParams.put("event", "event");
        HttpResponse formResponse = HttpClientUtil.post(formParams, null, formUrl);
        logger.info("callback:{}", parse((CloseableHttpResponse) formResponse));
        //文档列表 (post json)
        String fileUrl = "##";
        Map<String, String> heads = new HashMap<String, String>();
        heads.put("Cookie", cookie);
        Map<String, String> params = new HashMap<String, String>();
        params.put("billType", "CUSTOMER");
        params.put("billID", "585bbda9e4b0f4c83e484397");
        HttpResponse fileResponse = HttpClientUtil.postJson(params, heads, fileUrl);
        logger.info("fileList:{}", parse((CloseableHttpResponse) fileResponse));
        //文件上传 (post file)
        String fileUploadUrl = "##";
        File file = new File("C:\\Users\\kingdee\\Desktop\\1.xlsx");
        Map<String, Object> fileParams = new HashMap<String, Object>();
        fileParams.put("file", file);
        HttpResponse uploadFileResponse = HttpClientUtil.postFile(fileUploadUrl, fileParams, heads);
        logger.info("uploadFile:{}", parse((CloseableHttpResponse) uploadFileResponse));
    }

}
