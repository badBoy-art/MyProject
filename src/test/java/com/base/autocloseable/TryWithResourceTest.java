package com.base.autocloseable;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-05-29
 * @Description <a href="https://juejin.im/entry/57f73e81bf22ec00647dacd0"></a>
 */
public class TryWithResourceTest {

    @Test
    public void test() {
        //try-with-resource 语法糖
        try (Connection conn = new Connection()) {
            conn.sendData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
