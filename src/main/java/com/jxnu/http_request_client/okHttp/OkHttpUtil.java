package com.jxnu.http_request_client.okHttp;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author shoumiao_yao
 * @date 2016-12-29
 */
public class OkHttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    private final static OkHttpClient client = new OkHttpClient();
    private final static MediaType JSON = MediaType.parse("application/json");

    /**
     * get
     *
     * @param url 请求路径
     * @return
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        if (StringUtils.isBlank(url)) return null;
        //设置请求参数
        Request request = new Request.Builder().url(url).get().build();
        Response response = client.newCall(request).execute();
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
    public static Response post(Map<String, String> params, Map<String, String> heads, String url) throws IOException {
        if (CollectionUtils.isEmpty(params) || StringUtils.isBlank(url)) return null;
        //设置表单内容
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            builder.add(key, value);
        }
        //设置请求参数
        Request.Builder requestBuild = new Request.Builder().post(builder.build()).url(url);
        //设置请求头部
        if (!CollectionUtils.isEmpty(heads)) {
            Set<String> headKeys = heads.keySet();
            for (String key : headKeys) {
                String value = heads.get(key);
                requestBuild.addHeader(key, value);
            }
        }
        //获取响应
        Response response = client.newCall(requestBuild.build()).execute();
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
    public static Response postJson(Map<String, String> params, Map<String, String> heads, String url) throws IOException {
        if (CollectionUtils.isEmpty(params) || CollectionUtils.isEmpty(heads) || StringUtils.isBlank(url)) return null;
        //设置json
        String json = JSONObject.toJSONString(params);
        RequestBody requestBody = RequestBody.create(JSON, json);
        //设置请求参数
        Request.Builder requestBuild = new Request.Builder().post(requestBody).url(url);
        //设置请求头部
        if (!CollectionUtils.isEmpty(heads)) {
            Set<String> headKeys = heads.keySet();
            for (String key : headKeys) {
                String value = heads.get(key);
                requestBuild.addHeader(key, value);
            }
        }
        //获取响应
        Response response = client.newCall(requestBuild.build()).execute();
        return response;
    }

    /**
     * post json
     *
     * @param json 请求json body
     * @param url  请求路径
     * @return
     */
    public static Response postJson(String json, String url) throws IOException {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(url)) return null;
        //设置请求体内容
        RequestBody requestBody = RequestBody.create(JSON, json);
        //设置请求参数
        Request request = new Request.Builder().post(requestBody).url(url).build();
        Response response = client.newCall(request).execute();
        return response;
    }

    /**
     * post
     *
     * @param params      请求参数
     * @param heads       请求头部
     * @param url         请求路径
     * @param contentType 请求Content-Type
     * @return
     * @throws IOException
     */
    public static Response post(String params, Map<String, String> heads, String url, String contentType) throws IOException {
        if (StringUtils.isBlank(params) || CollectionUtils.isEmpty(heads) || StringUtils.isBlank(url)) return null;
        //设置表单内容
        RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), params);
        //设置请求参数
        Request.Builder requestBuild = new Request.Builder().post(requestBody).url(url);
        //设置请求头部
        Set<String> headKeys = heads.keySet();
        for (String key : headKeys) {
            String value = heads.get(key);
            requestBuild.addHeader(key, value);
        }
        //获取响应
        Response response = client.newCall(requestBuild.build()).execute();
        return response;
    }

    /**
     * 文件上传
     *
     * @param url         请求路径
     * @param params      请求参数
     * @param heads       请求头
     * @param contentType Content-Type
     * @return
     * @throws IOException
     */
    public static Response postFile(String url, Map<String, Object> params, Map<String, String> heads, String contentType) throws IOException {
        if (StringUtils.isBlank(url) || CollectionUtils.isEmpty(params) || StringUtils.isBlank(contentType))
            return null;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            Object value = params.get(key);
            if (value instanceof File) {
                File file = (File) value;
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(contentType), file));
            } else {
                builder.addFormDataPart(key, String.valueOf(value));
            }
        }
        Request.Builder requestBuild = new Request.Builder().post(builder.build()).url(url);
        if (!CollectionUtils.isEmpty(heads)) {
            Set<String> headKeys = heads.keySet();
            for (String headKey : headKeys) {
                String headValue = heads.get(headKey);
                requestBuild.header(headKey, headValue);
            }
        }
        Response response = client.newCall(requestBuild.build()).execute();
        return response;
    }

    public static void main(String[] args) throws IOException {
        String host = "localhost:8080";
        //登录系统 获取cookie (get 请求)
        String testLoginUrl = "###";
        Response response = OkHttpUtil.get(testLoginUrl);
        String cookie = response.headers().get("Set-Cookie");
        //(post 表单)
        String formUrl="###";
        Map<String,String> formParams=new HashMap<String, String>();
        formParams.put("sn","sn");
        formParams.put("event","event");
        Response formResponse=OkHttpUtil.post(formParams,null,formUrl);
        logger.info("callback:{}",formResponse.body().string());
        //文档列表 (post json)
        String fileUrl = "###";
        Map<String, String> heads = new HashMap<String, String>();
        heads.put("Cookie", cookie);
        Map<String, String> params = new HashMap<String, String>();
        params.put("billType", "CUSTOMER");
        params.put("billID", "585bbda9e4b0f4c83e484397");
        Response fileResponse = OkHttpUtil.postJson(params, heads, fileUrl);
        logger.info("fileList:{}", fileResponse.body().string());
        //文件上传 (post file)
        String fileUploadUrl = "###";
        File file = new File("C:\\Users\\kingdee\\Desktop\\1.xlsx");
        Map<String, Object> fileParams = new HashMap<String, Object>();
        fileParams.put("file", file);
        Response uploadFileResponse = OkHttpUtil.postFile(fileUploadUrl, fileParams, heads, "text/x-markdown");
        logger.info("uploadFile:{}", uploadFileResponse.body().string());
    }

}
