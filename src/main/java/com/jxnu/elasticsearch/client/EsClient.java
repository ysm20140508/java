package com.jxnu.elasticsearch.client;

import org.elasticsearch.client.Client;

/**
 * @author shoumiao_yao
 * @date 2016-09-23
 */
public interface EsClient {

    /**
     * 返回客户端
     * @return
     */
    public Client getClient();
}
