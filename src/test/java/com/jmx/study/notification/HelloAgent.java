package com.jmx.study.notification;

import com.jmx.study.standard.Hello;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author xuedui.zhao
 * @create 2018-12-17
 */
public class HelloAgent {

    public static void main(String[] args) throws Exception{
        //调用ManagementFactory的方法创建MBeanServer
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        //创建MBean在MBeanServer中的name，最为其唯一标识
        ObjectName helloName = new ObjectName("com.jmx.study.notification:name=hello");
        //register mbean
        server.registerMBean(new Hello(), helloName);

        //Thread.sleep(Long.MAX_VALUE);

        //注册一个端口，绑定url.客户端就可以使用rmi通过url方式来连接JMXConnectorServer
        Registry registry = LocateRegistry.createRegistry(1090);

        //构造JMXServiceURL
        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1090/jmxrmi");
        //创建JMXConnectorServer,并注册到MBean Server
        JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, server);
        //启动 Connector
        cs.start();

    }
}
