package com.virtualmachine.study;

import static com.alibaba.csp.sentinel.util.TimeUtil.currentTimeMillis;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.LockSupport;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.junit.Test;

import com.sun.tools.attach.VirtualMachine;

import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;
import sun.misc.VM;

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
        String targetVmPid = "74168";
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

    /**
     * java程序自身进程ID
     */
    @Test
    public void getSelfProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        System.out.println(Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue());
    }

    /**
     * 获取所有正在运行着的Java进程
     */
    @Test
    public void getAllProcessID() throws MonitorException, URISyntaxException {
        // 获取监控主机
        MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
        // 取得所有在活动的虚拟机集合
        Set<?> vmlist = new HashSet<Object>(local.activeVms());
        // 遍历集合，输出PID和进程名
        for (Object process : vmlist) {
            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
            // 获取类名
            String processname = MonitoredVmUtil.mainClass(vm, true);
            System.out.println(process + " ------> " + processname);
        }
    }

    /**
     * 根据类找到对应Java进程ID
     *
     * @throws MonitorException
     * @throws URISyntaxException
     */
    @Test
    public void getProcessIDByClassName() throws MonitorException, URISyntaxException {
        Class cls = VirtualMachineStudy.class;
        // 获取监控主机
        MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
        // 取得所有在活动的虚拟机集合
        Set<?> vmlist = new HashSet<Object>(local.activeVms());
        // 遍历集合，输出PID和进程名
        for (Object process : vmlist) {
            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
            // 获取类名
            String processname = MonitoredVmUtil.mainClass(vm, true);
            if (cls.getName().equals(processname)) {
                System.out.println(((Integer) process).intValue());
            }
        }
        System.out.println("over");
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println(currentTimeMillis());
            LockSupport.parkUntil(currentTimeMillis() + 2000);
        }
    }

}
