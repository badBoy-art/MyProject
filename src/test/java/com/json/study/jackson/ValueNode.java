package com.json.study.jackson;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-02
 * @Description
 */
@JsonSerialize(using = JacksonSupport.ValueNodeSerializer.class)
@JsonDeserialize(using = JacksonSupport.ValueNodeDeserializer.class)
public interface ValueNode extends Iterable<ValueNode>, Serializable {

    enum Type {
        list, map, bool, number, string, NULL
    }

    Type getType();

    List<ValueNode> find(String... path);

    List<ValueNode> find(int count, String... path);

    ValueNode firstChild(String... path);

    ValueNode get(int childIndex);

    int size();

    ValueNode get(String childName);

    Set<Entry<String, ValueNode>> entrySet();

    Set<String> keySet();

    Date toDateValue();

    Number toNumber();

    Boolean toBooleanValue();

    String toJson();

    int getIntValue(int def);

    float getFloatValue(float def);

    long getLongValue(long def);

    Map<Path, ValueNode> flatten();

    List<DiffValue> diff(ValueNode other);

    boolean contains(ValueNode other, Set<Path> ignores);

    interface DiffValue extends Comparable<DiffValue> {

        boolean getAction();

        Path getPath();

        ValueNode getValue();
    }
}
