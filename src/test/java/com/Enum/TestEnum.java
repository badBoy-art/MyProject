package com.Enum;

import java.util.Set;

import org.junit.Test;
import org.reflections.Reflections;

import com.alibaba.fastjson.JSON;

/**
 * @author xuedui.zhao
 * @create 2018-09-27
 */
public class TestEnum {

    @Test
    public void test() {
        Reflections reflections = new Reflections("com.Enum");
        Set<Class<? extends BaseEnum>> subTypes = reflections.getSubTypesOf(BaseEnum.class);
        subTypes.forEach(x -> {
            if (x.getSimpleName().equalsIgnoreCase(BaseTypeEnum.DAY_FIRST.getBaseEnums().get(0).get())) {
                System.out.println(x.getEnumConstants());
            }
        });
    }

    @Test
    public void testToEnum() {
        EnumStudy enumStudy = new EnumStudy();
        enumStudy.setCodeEnum(CodeEnum.EXPOSURE_TICKET);
        enumStudy.setName("zhangsan");

        System.out.println(JSON.toJSONString(enumStudy));
        String str = "{\"codeEnum\":1,\"name\":\"zhangsan\"}";

        System.out.println(JSON.parseObject(str, EnumStudy.class));
    }
}
