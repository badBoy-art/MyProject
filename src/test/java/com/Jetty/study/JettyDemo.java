package com.Jetty.study;

import org.junit.Test;
import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpServer;
import org.mortbay.http.SocketListener;
import org.mortbay.http.handler.ResourceHandler;

/**
 * @author xuedui.zhao
 * @create 2018-10-09
 */
public class JettyDemo {
    
    @Test
    public void test01() {
        try {
            //创建JettyHttpServer对象
            HttpServer server=new HttpServer();
            //在端口8080上给HttpServer对象绑上一个listener，使之能够接收HTTP请求
            SocketListener listener=new SocketListener();
            listener.setPort(8080);
            server.addListener(listener);
            //创建一个HttpContext，处理HTTP请求。
            HttpContext context=new HttpContext();
            //用setContextPath把Context映射到（/web）URL上。
            context.setContextPath("/web");
            //setResourceBase方法设置文档目录以提供资源
            context.setResourceBase("C:\\j2sdk1.4.1_05");
            //添加资源处理器到HttpContext，使之能够提供文件系统中的文件
            context.addHandler(new ResourceHandler());
            server.addContext(context);
            //启动服务器
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
