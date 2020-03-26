package com.spring.resolvableType.study;

import com.parameterNameDiscoverer.study.Main3;
import org.junit.Test;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;

/**
 * @author xuedui.zhao
 * @create 2019-08-19
 */
public class ResolvableTypeStudy {

    @Test
    public void testForClass() {
        ResolvableType type = ResolvableType.forClass(Main3.class);
        System.out.println(type);
        System.out.println(type.getType());
        //?
        System.out.println(type.getComponentType());
        System.out.println(type.getInterfaces());
        System.out.println(type.getSuperType());
        System.out.println(type.getRawClass());
        System.out.println(type.getSource());
        System.out.println(type.getGenerics());
        System.out.println(type.resolve());
    }

    /**
     * 获取指定方法的返回值的类型
     *
     * @throws Exception
     */
    @Test
    public void testForMethodReturnType() throws Exception {
        Method method = Main3.class.getMethod("testArgName", String.class, Integer.class);
        ResolvableType type = ResolvableType.forMethodReturnType(method, Main3.class);
        System.out.println(type);
    }
}
