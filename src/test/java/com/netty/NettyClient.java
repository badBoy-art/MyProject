package com.netty;

import java.net.URI;

import org.springframework.stereotype.Service;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-02-22
 * @Description
 */
@Service
public class NettyClient {
    private Channel channel;

    public void run(String strUri) {
        new Thread(() -> runClient(strUri)).start();

    }

    private void runClient(String strUri) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            URI uri = new URI(strUri);
            String protocol = uri.getScheme();
            if (!"ws".equals(protocol)) {
                throw new IllegalArgumentException("Unsupported protocol: " + protocol);
            }
            HttpHeaders customHeaders = new DefaultHttpHeaders();
            customHeaders.add("MyHeader", "MyValue");
            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            final MyWebSocketClientHandler handler = new MyWebSocketClientHandler(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, false, customHeaders));
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("http-codec", new HttpClientCodec());
                    pipeline.addLast("aggregator", new HttpObjectAggregator(8192));
                    pipeline.addLast("ws-handler", handler);
                }
            });
            System.out.println("===============Message客户端启动===============");
            channel = b.connect(uri.getHost(), uri.getPort()).sync().channel();
            handler.handshakeFuture().sync();
            channel.closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }

    private Channel connect(URI uri) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        String protocol = uri.getScheme();
        if (!"ws".equals(protocol)) {
            throw new IllegalArgumentException("Unsupported protocol: " + protocol);
        }
        HttpHeaders customHeaders = new DefaultHttpHeaders();
        customHeaders.add("MyHeader", "MyValue");
        final MyWebSocketClientHandler handler = new MyWebSocketClientHandler(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, false, customHeaders));
        b.group(group);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.TCP_NODELAY, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("http-codec", new HttpClientCodec());
                pipeline.addLast("aggregator", new HttpObjectAggregator(8192));
                pipeline.addLast("ws-handler", handler);
            }
        });
        System.out.println("===============Message客户端启动===============");
        ChannelFuture future = b.connect(uri.getHost(), uri.getPort()).sync();
        if (!future.isSuccess()) {
            throw new IllegalArgumentException("Unsupported protocol: " + future.cause());
        }
        Channel channel = future.channel();
        channel.closeFuture().addListener((ChannelFutureListener) cdf -> {
            //链接关闭，清空链接缓存
        });
        return channel;

    }
}
