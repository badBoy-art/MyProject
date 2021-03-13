package com.guava.study;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * @author xuedui.zhao
 * @create 2018-12-13
 */
public class JoinerSplitterStudy {

    @Test
    public void test() {
        System.out.println(Joiner.on(',').skipNulls().join("", null, 1, 2, 3));
        System.out.println(Joiner.on(",").useForNull("null").join("", null, 1, 2, 3));
        System.out.println(Joiner.on(',').skipNulls().join("", "", 1, 2, 3));
        System.out.println(Joiner.on(',').join("", "", 1, 2, 3));
    }

    @Test
    public void testList() {
        List<String> list = Lists.newArrayList("zhangsan", "lisi", "wangwu");
        System.out.println(Joiner.on(",").join(list));
    }

    @Test
    public void testMap() {
        Map<String, String> map = ImmutableMap.of("zhang", "san", "li", "si");
        System.out.println(Joiner.on("-").withKeyValueSeparator("=").join(map));
    }

    @Test
    public void testOnPattern() {
        String input = "aa.dd,,ff,,.";
        List<String> result = Splitter.onPattern("[.|,]").omitEmptyStrings().splitToList(input);
        System.out.println(result);
    }

    @Test
    public void testSplitterMap() {
        String str = "xiaoming=11,xiaohong=23";
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(str);
        System.out.println(map);
    }

    @Test
    public void testCharMatcher() {
        // 判断匹配结果
        boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K'); //true
        System.out.println(result);
        // 保留数字文本  CharMatcher.digit() 已过时   retain 保留
        //String s1 = CharMatcher.digit().retainFrom("abc 123 efg"); //123
        String s1 = CharMatcher.inRange('0', '9').retainFrom("abc 123 efg"); // 123
        System.out.println(s1);
        // 删除数字文本  remove 删除
        String s2 = CharMatcher.inRange('0', '9').removeFrom("abc 123 efg"); // abc  efg
        System.out.println(s2);
        String s3 = CharMatcher.digit().removeFrom("abc 123 efg");    //abc  efg

        System.out.println(s3);

        String address = "河北省沧州市泊头市红旗大街441";
        address = CharMatcher.anyOf("中国").trimLeadingFrom(address);
        address = CharMatcher.anyOf("河北省").trimLeadingFrom(address);
        address = CharMatcher.anyOf("沧州市").trimLeadingFrom(address);
        System.out.println(address);
    }
}
