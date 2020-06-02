package com.json.study.jackson.mapper;

/**
 * Created by zhaohui.yu
 * 15/9/9
 */
public class ToStringJsonWriter implements JsonWriter {
    @Override
    public String write(Object value) {
        if (value == null) return "";
        return value.toString();
    }
}
