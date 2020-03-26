package com.http.study;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author badBoy
 * @create 2019-09-23
 */
public class SharedHttpAsyncClient {

    public static void main(String[] args) throws Exception {
        CloseableHttpAsyncClient client1 = null;
        CloseableHttpAsyncClient client2 = null;
        try {
            PoolingNHttpClientConnectionManager connMgr = createConnectionManager();
            client1 = HttpAsyncClientBuilder.create()
                    .setConnectionManager(connMgr)
                    .build();
            if (!client1.isRunning()) {
                System.out.println("starting client 1");
                client1.start();
            }
            client2 = HttpAsyncClientBuilder.create()
                    .setConnectionManager(connMgr)
                    .build();
            if (!client2.isRunning()) {
                System.out.println("starting client 2");
                client2.start();
            }

            makeRequest(client1);
            makeRequest(client2);

        } finally {
            // Release only once because they share the connection manager
            if (client2 != null)
                client2.close();
        }
    }

    private static void makeRequest(CloseableHttpAsyncClient client) throws ExecutionException,
            InterruptedException {
        HttpGet request1 = new HttpGet("http://www.apache.org/");
        Future<HttpResponse> future = client.execute(request1, null);
        HttpResponse response1 = future.get();
        System.out.println(request1.getRequestLine() + "->" + response1.getStatusLine());
    }

    private static PoolingNHttpClientConnectionManager createConnectionManager()
            throws IOReactorException {

        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setSoTimeout(3000)
                .setTcpNoDelay(true)
                .setSoKeepAlive(true)
                .build();

        ConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(ioReactorConfig);

        PoolingNHttpClientConnectionManager connMgr = new PoolingNHttpClientConnectionManager(
                ioreactor);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(10);

        return connMgr;
    }

}
