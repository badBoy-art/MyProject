package com.resilience4j.study;

/**
 * BackendService
 *
 * @author xuedui.zhao
 * @create 2018-12-01
 */
public class BackendServiceImpl implements BackendService{
    @Override
    public String doSomething() {
        return "doSomething";
    }
}
