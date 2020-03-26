package com.snow.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author badBoy
 * @create 2019-09-06
 */
public class TestAgent {

    /**
     * JVM启动
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new TestTransformer());
    }

    /**
     * JVM运行时Attach调用
     */
    public static void agentmain(String args, Instrumentation inst) {
        //指定我们自己定义的Transformer，在其中利用Javassist做字节码替换
        inst.addTransformer(new TestTransformer(), true);
        try {
            //重定义类并载入新的字节码
            inst.retransformClasses(Base.class);
            System.out.println("Agent Load Done.");
        } catch (Exception e) {
            System.out.println("agent load failed!");
        }
    }

}
