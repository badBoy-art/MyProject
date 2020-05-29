package com.base.autocloseable;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-05-29
 * @Description
 */
public class Connection implements AutoCloseable {

    public void sendData() {
        System.out.println("正在发送数据");
    }

    @Override
    public void close() throws Exception {
        System.out.println("正在关闭连接");
    }

}
