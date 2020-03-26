package com.ognl.study;

import ognl.Ognl;
import ognl.OgnlContext;
import org.junit.Test;

/**
 * ognl学习测试
 *
 * @author xuedui.zhao
 * @create 2018-11-27
 */
public class OgnlStudy {

    @Test
    public void test() {
        try {
            OgnlContext context = new OgnlContext(null,null,new MyMemberAccess(true));
            Object tail = Ognl.getValue("substring(0,4)",context, "ognl3.2.6MemeberAccess");
            Constant constant = new Constant();
            Object obj = Ognl.getValue("getString()",context, constant);
            System.out.println(obj);
            System.out.println(tail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Constant{
    public final static String ONE = "one";

    public static void get() {

    }

    public static String getString() {
        return "String";
    }
}