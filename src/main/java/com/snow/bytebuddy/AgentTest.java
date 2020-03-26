package com.snow.bytebuddy;

/**
 * mvn clean compile package -Dmaven.test.skip=true
 *
 * @author xuedui.zhao
 * @create 2018-06-07
 */
public class AgentTest {

    public static void main(String[] args) throws Exception {
        ByteService realService = new ByteService();
        realService.sayHello();
    }
}
