package com.jxnu.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author shoumiao_yao
 * @date 2016-08-22
 */
public class ClientFactory {

    public static TransportClient createTransportClient(Map<String, String> map, String host, Integer port) {
        Settings settings = Settings.builder().put(map).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, port)));
        return client;
    }

    public static TransportClient createTransportClient(String host,Integer port){
        TransportClient client = TransportClient.builder().build();
        client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, port)));
        return client;
    }
}
