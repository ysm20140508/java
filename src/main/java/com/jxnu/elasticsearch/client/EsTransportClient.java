package com.jxnu.elasticsearch.client;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.Build;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @author shoumiao_yao
 * @date 2016-09-23
 */
@Component(value = "transportClient")
public class EsTransportClient implements EsClient {
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private Integer port;
    @Value("${cluster.name}")
    private String clusterName;
    @Value("${client.transport.sniff}")
    private Boolean clientTransportSniff;
    private TransportClient client;


    @PostConstruct
    public Client init() {
        Settings.Builder build = Settings.builder();
        if (StringUtils.isNotEmpty(clusterName)) {
            build.put("cluster.name", clusterName);
        }
        if (clientTransportSniff != null) {
            build.put("client.transport.sniff", clientTransportSniff);
        }
        Settings settings = build.build();
        this.client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, port)));
        return client;
    }

    public Client getClient() {
        return client;
    }
}
