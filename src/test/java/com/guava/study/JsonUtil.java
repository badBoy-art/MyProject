package com.guava.study;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2021-04-21
 * @Description
 */
public class JsonUtil {

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .disableHtmlEscaping()
            .create();

    /**
     * 对象转json自符串
     *
     * @param obj 对象
     */
    public static String bean2Json(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * json 转对象
     *
     * @param jsonStr  json字符串
     * @param objClass 对象类型
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return gson.fromJson(jsonStr, objClass);
    }

    /**
     * 格式化json字符串
     *
     * @param uglyJsonStr 不规整的json字符串
     */
    public static String jsonFormatter(String uglyJsonStr) {
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJsonStr);
        return gson.toJson(je);
    }

    /**
     * json转list
     *
     * @param json 数组格式的json字符串
     * @param t    单个数组元素的类型
     */
    public static <T> List<T> json2BeanList(String json, Class<T> t) {
        List<T> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray jsonarray = parser.parse(json).getAsJsonArray();
        for (JsonElement element : jsonarray) {
            list.add(gson.fromJson(element, t));
        }
        return list;
    }

    /**
     * json转Map
     *
     * @param json 键值对格式的json字符串
     * @param type Map<T,T> 类型
     *             eg:
     *             Map<String, Integer> jsonMap = JsonUtils.json2Map(json,
     *             new TypeToken<Map<String, Integer>>() { }.getType());
     */
    public static Map json2Map(String json, Type type) {
        return gson.fromJson(json, type);
    }

    /**
     * string -> jsonObject
     *
     * @param str
     * @return
     */
    public static JsonObject stringToJsonObject(String str) {
        JsonParser parser = new JsonParser();
        return parser.parse(str).getAsJsonObject();
    }

    public static <T> T json2BeanV2(JsonElement jsonElement, Class<T> objClass) {
        return gson.fromJson(gson.toJson(jsonElement), objClass);
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        Map<Long, Map<String, String>> map2 = new HashMap<>();
        map2.put(1L, map);
        String json = "{\"44354219006\":{},\"42078602241\":{},\"47452609187\":{},\"43486211501\":{}}";
        System.out.println(json);
        Map<Long, Map<String, String>> map3 = JsonUtil.json2Map(json, new TypeToken<Map<Long, Map<String, String>>>() {
        }.getType());
        System.out.println(map3);
    }

}
