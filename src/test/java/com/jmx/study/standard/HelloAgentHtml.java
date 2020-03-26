package com.jmx.study.standard;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author xuedui.zhao
 * @create 2018-12-17
 */
public class HelloAgentHtml {

  public static void main(String[] args) throws Exception {

      //调用ManagementFactory的方法创建MBeanServer
      MBeanServer server = ManagementFactory.getPlatformMBeanServer();
      //创建MBean在MBeanServer中的name，最为其唯一标识
      ObjectName helloName = new ObjectName("com.jmx.study.standard:name=hello");
      //register mbean
      server.registerMBean(new Hello(), helloName);

      //创建HTML adaptor
      ObjectName adapterName = new ObjectName("jmxBean:name=htmladapter,port=8082");
      HtmlAdaptorServer adapter = new HtmlAdaptorServer();
      server.registerMBean(adapter, adapterName);
      adapter.start();

  }
}
