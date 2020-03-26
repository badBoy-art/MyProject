package com.assist.study;

import com.Extends.study.WhiteDog;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2018-05-31
 */
public class MyAssist {

    @Test
    public void test01() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.Extends.study.WhiteDog");

        String mathodName = "jump";
        CtMethod method = cc.getDeclaredMethod(mathodName);
        method.insertBefore("System.out.print(\"before jump\");");

        cc.writeFile("/Users/xuedui.zhao/Desktop/myjava");
        System.out.println("更简单的操作java字节码文件的jar包javaAssist操作类成功");

    }

}
