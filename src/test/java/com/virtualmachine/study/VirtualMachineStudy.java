package com.virtualmachine.study;


import com.sun.tools.attach.VirtualMachine;
import org.junit.Test;
import sun.misc.VM;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;

/**
 * https://zheng12tian.iteye.com/blog/1495037
 *
 * @author xuedui.zhao
 * @create 2019-03-15
 */
public class VirtualMachineStudy {

    @Test
    public void testVirtualMachine() throws Exception {
        // 被监控jvm的pid(windows上可以通过任务管理器查看  linux:jps)
        String targetVmPid = "76167";
        // Attach到被监控的JVM进程上
        VirtualMachine virtualmachine = VirtualMachine.attach(targetVmPid);

        // 让JVM加载jmx Agent
        String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");
        System.out.println("javaHome === " + javaHome);
        String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
        System.out.println("jmxAgent === " + jmxAgent);
        virtualmachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");

        // 获得连接地址
        Properties properties = virtualmachine.getAgentProperties();
        String address = (String) properties.get("com.sun.management.jmxremote.localConnectorAddress");
        System.out.println("address === " + address);

        // Detach
        virtualmachine.detach();
        // 通过jxm address来获取RuntimeMXBean对象，从而得到虚拟机运行时相关信息
        JMXServiceURL url = new JMXServiceURL(address);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), "java.lang:type=Runtime",
                RuntimeMXBean.class);
        // 得到目标虚拟机占用cpu时间
        System.out.println(rmxb.getUptime());
        System.out.println(rmxb.getName());
    }

    @Test
    public void testVM() {
       // Class var0 = Reflection.getCallerClass();
        System.out.println(VM.isSystemDomainLoader(ClassLoader.getSystemClassLoader()));
    }
}
