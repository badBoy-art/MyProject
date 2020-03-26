package com.hystrix.study;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author xuedui.zhao
 * @create 2019-01-14
 */
public class CommandHelloWorld extends HystrixCommand<String> {

    private String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExamplegRroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + " !";
    }

    @Override
    protected String getFallback() {
        return "fail";
    }
}
