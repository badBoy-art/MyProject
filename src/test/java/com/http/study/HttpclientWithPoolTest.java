package com.http.study;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * TLS的握手由客户端发送Client Hello消息开始，服务端返回Server Hello结束，整个流程中提供了2种不同的会话复用机制，这个地方就简单看一下，知道有这么一回事：
 * <p>
 * session id会话复用----对于已建立的TLS会话，
 * 使用session id为key（来自第一次请求的Server Hello中的session id），主密钥为value组成一对键值对保存在服务端和客户端的本地。
 * 当第二次握手时，客户端如果想复用会话，则发起的Client Hello中带上session id，服务端收到这个session id检查本地是否存在，有则允许会话复用，进行后续操作
 * <p>
 * session ticket会话复用----一个session ticket是一个加密的数据blob，
 * 其中包含需要重用的TLS连接信息如session key等，它一般使用ticket key加密，因为ticket key服务端也知道，
 * 在初始化握手中服务端发送一个session ticket到客户端并存储到客户端本地，当会话重用时，客户端发送session ticket到服务端，服务端解密成功即可复用会话
 *
 * @author xuedui.zhao
 * @create 2019-08-08
 */
public class HttpclientWithPoolTest extends BaseHttpClientTest {

    private CloseableHttpClient httpClient = null;

    @Before
    public void before() {
        initHttpClient();
    }

    @Test
    public void test() throws Exception {
        startUpAllThreads(getRunThreads(new HttpThread()));
        // 等待线程运行
        for (; ; ) ;
    }

    private class HttpThread implements Runnable {

        @Override
        public void run() {
            HttpGet httpGet = new HttpGet("https://www.baidu.com/");
            // 长连接标识，不加也没事，HTTP1.1默认都是Connection: keep-alive的
            httpGet.addHeader("Connection", "keep-alive");

            long startTime = System.currentTimeMillis();
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                addCost(System.currentTimeMillis() - startTime);

                if (NOW_COUNT.incrementAndGet() == REQUEST_COUNT) {
                    System.out.println(EVERY_REQ_COST.toString());
                }
            }
        }

    }

    private void initHttpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 总连接池数量
        connectionManager.setMaxTotal(100);
        // 可为每个域名设置单独的连接池数量
        connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("www.baidu.com")), 20);
        // 设置tcp socket的数据
        SocketConfig socketConfig =  SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
        connectionManager.setDefaultSocketConfig(socketConfig);
        // setConnectTimeout表示设置建立连接的超时时间
        // setConnectionRequestTimeout表示从连接池中拿连接的等待超时时间
        // setSocketTimeout表示发出请求后等待对端应答的超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(2000)
                .setSocketTimeout(3000).build();
        // 重试处理器，StandardHttpRequestRetryHandler这个是官方提供的，看了下感觉比较挫，很多错误不能重试，可自己实现HttpRequestRetryHandler接口去做
        HttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler();

        httpClient = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retryHandler).setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE).build();

        // 服务端假设关闭了连接，对客户端是不透明的，HttpClient为了缓解这一问题，在某个连接使用前会检测这个连接是否过时，如果过时则连接失效，但是这种做法会为每个请求
        // 增加一定额外开销，因此有一个定时任务专门回收长时间不活动而被判定为失效的连接，可以某种程度上解决这个问题
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    // 关闭失效连接并从连接池中移除
                    connectionManager.closeExpiredConnections();
                    // 关闭30秒钟内不活动的连接并从连接池中移除，空闲时间从交还给连接管理器时开始
                    connectionManager.closeIdleConnections(20, TimeUnit.SECONDS);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }, 0, 1000 * 5);
    }

}
