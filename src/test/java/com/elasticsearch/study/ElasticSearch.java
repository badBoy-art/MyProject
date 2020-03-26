package com.elasticsearch.study;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.Test;

import java.net.InetAddress;

/**
 * @author xuedui.zhao
 * @create 2018-10-11
 */
public class ElasticSearch {

    @Test
    public void testPut() {
    
    }

    @Test
    public void TestRestClient() {
        try {

            HttpHost httpHost = new HttpHost(InetAddress.getByName("localhost"), 9200, "http");
            HttpHost[] httpHosts = new HttpHost[1];
            httpHosts[0] = httpHost;
            Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
            RestClientBuilder restClientBuilder =
            RestClient.builder(httpHosts).setDefaultHeaders(defaultHeaders)
               .setMaxRetryTimeoutMillis(10 * 1000);

            RestClient restClient = restClientBuilder.setFailureListener(new RestClient.FailureListener() {
                @Override
                public void onFailure(HttpHost host) {
                    super.onFailure(host);
                }
            }).build();

            //restClient.performRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
