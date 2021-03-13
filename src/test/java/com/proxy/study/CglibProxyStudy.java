package com.proxy.study;

import org.junit.Test;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * 可以直接代理类，使用字节码技术，不能对 final类进行继承。使用了动态生成字节码技术。
 *
 * @author xuedui.zhao
 * @create 2018-11-24
 */
public class CglibProxyStudy {

    @Test
    public void test() throws Exception {
        //生成的代理类保存到磁盘
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Cglib cglibTest = new Cglib();
        System.out.println("cglib = " + cglibTest.getClass());
        Enhancer enhancer = new Enhancer();
        Callback s = new MthdInvoker(cglibTest);

        enhancer.setSuperclass(Cglib.class);
        Callback callbacks[] = new Callback[]{s};
        enhancer.setCallbacks(callbacks);
        Cglib cglibTestProxy = (Cglib) enhancer.create();

        System.out.println("proxy = " + cglibTestProxy.getClass());
        cglibTestProxy.gotoHome();
        cglibTestProxy.gotoSchool();
        //这里可以看到这个类以及被代理，在执行方法前会执行aopMethod（）。这里需要注意的是oneDay（）方法和onedayFinal（）的区别。onedayFinal的方法aopMethod执行2次，oneDay的aopMethod执行1次 ,注意这里和jdk的代理的区别
        cglibTestProxy.oneday();
        cglibTestProxy.onedayFinal();
        System.out.println("---------");
        cglibTestProxy.finalMethod();

//        byte[] bs = DefaultGeneratorStrategy.INSTANCE.generate(enhancer);
//        FileOutputStream fileOutputStream = new FileOutputStream("/Users/xuedui.zhao/Projects/myProject/src/test/java/com/proxy/study/CglibProxyStudy.class");
//        fileOutputStream.write(bs);
//        fileOutputStream.flush();
//        fileOutputStream.close();

    }
}
