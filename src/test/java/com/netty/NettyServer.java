package com.netty;

import org.springframework.stereotype.Service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-02-22
 * @Description <href a="https://www.toutiao.com/i6794445371457143307/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&timestamp=1582364097&app=news_article&utm_source=weixin&utm_medium=toutiao_ios&req_id=202002221734560100140470732763E098&group_id=6794445371457143307"></href>
 */
@Service
public class NettyServer {
    public void run(int port) {
        new Thread() {
            public void run() {
                runServer(port);
            }
        }.start();
    }

    private void runServer(int port) {
        System.out.println("===============Message服务端启动===============");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("codec-http", new HttpServerCodec());
                    pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                    pipeline.addLast("handler", new MyWebSocketServerHandler());
                }
            });
            Channel ch = b.bind(port).sync().channel();
            System.out.println("Message服务器启动成功：" + ch.toString());
            ch.closeFuture().sync();
        } catch (Exception e) {
            System.out.println("Message服务运行异常：" + e.getMessage());
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("Message服务已关闭");
        }
    }
}
