package com.Enum;

import com.alibaba.fastjson.JSON;

/**
 * @author xuedui.zhao
 * @create 2018-12-13
 */
public class EnumToJsonUtil {

    public static String toJSONString(Object obj) {
      String className =  obj.getClass().getName();
      String enumName = JSON.toJSONString(obj);
        EnumUtil enumUtil = new EnumUtil();
        enumUtil.setClassName(className);
        enumUtil.setEnumName(enumName.substring(1,enumName.length()-1));
        return JSON.toJSONString(enumUtil);

    }
}
