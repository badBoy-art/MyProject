package com.proxy.study;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * 常见是mybatis的mapper文件是代理。使用反射完成。使用了动态生成字节码技术
 *
 * @author xuedui.zhao
 * @create 2018-11-24
 */
public class JdkProxyStudy {

    @Test
    public void test() throws Exception {
        //生成的代理类保存到磁盘
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Interface interface1 = new InterfaceImpl();

        Interface interfaceProxy = (Interface) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(), new Class[]{Interface.class},
                new MyInvocationHandler(interface1));

        //这里可以看到这个类以及被代理，在执行方法前会执行aopMethod（）。这里需要注意的是oneDay（）方法和oneDayFinal（）的区别
        interfaceProxy.gotoSchool();
        interfaceProxy.gotoWork();
        interfaceProxy.oneDay();
        interfaceProxy.oneDayFinal();

        // proxyName 为类名，interfaces为顶层接口Class
        //如果需要看，可以将字节码写入文件进行观察
//        Class[] interfaces = new Class[] { Interface.class };
//        byte[] proxyClassFile = ProxyGenerator.generateProxyClass("InterfaceImpl", interfaces);
//        File file = new File("/Users/xuedui.zhao/Projects/myProject/src/test/java/com/proxy/study/JdkProxyStudy.class");
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        fileOutputStream.write(proxyClassFile);
//        fileOutputStream.flush();
//        fileOutputStream.close();

    }
}
