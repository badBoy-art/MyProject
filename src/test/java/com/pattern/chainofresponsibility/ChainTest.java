package com.pattern.chainofresponsibility;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-16
 * @Description
 */
public class ChainTest {

    @Test
    public void test() {
        Handler zhangsan = new Director("张三");
        Handler lisi = new Manager("李四");
        Handler wangwu = new TopManager("王五");

        // 创建责任链
        zhangsan.setNextHandler(lisi);
        lisi.setNextHandler(wangwu);

        // 发起请假申请
        boolean result1 = zhangsan.process(new LeaveRequest("badBoy", 1));
        System.out.println("最终结果：" + result1 + "\n");

        boolean result2 = zhangsan.process(new LeaveRequest("badBoy", 4));
        System.out.println("最终结果：" + result2 + "\n");

        boolean result3 = zhangsan.process(new LeaveRequest("badBoy", 8));
        System.out.println("最终结果：" + result3 + "\n");
    }

}
