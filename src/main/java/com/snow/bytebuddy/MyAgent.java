package com.snow.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

/**
 * @author xuedui.zhao
 * @create 2018-06-07
 */
public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("this is an perform monitor agent.");
    new AgentBuilder.Default()
        .type(ElementMatchers.nameStartsWith("com.snow.bytebuddy"))
        .transform(
            (builder, type, classLoader, module) ->
                builder
                    .method(ElementMatchers.named("sayHello"))
                    .intercept(
                        MethodDelegation.to(ByteBuddyInterceptor.class)))
        .installOn(inst);
    }
}
