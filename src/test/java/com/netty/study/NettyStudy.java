package com.netty.study;

/**
 * Netty学习
 *
 * @author xuedui.zhao
 * @create 2018-12-12
 */
public class NettyStudy {

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer(8081);// 8081为启动端口
        server.start();
    }

}
