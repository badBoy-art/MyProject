package com.jodd;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.junit.Test;

import cn.hutool.core.comparator.PinyinComparator;
import cn.hutool.core.util.StrUtil;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-03-02
 * @Description
 */
public class HutoolTest {

    @Test
    public void test01() {
        System.out.println(StrUtil.BACKSLASH);
    }

    @Test
    public void test02() {
        PinyinComparator comparator = new PinyinComparator();
        List<String> arrs = Lists.newArrayList("乔峰", "郭靖", "杨过", "张无忌", "韦小宝", "张有忌", "张有才");
        // 使根据指定比较器产生的顺序对指定对象数组进行排序。
        arrs = arrs.stream().sorted(comparator).collect(Collectors.toList());
        for (String str : arrs) {
            System.out.println(str);
        }

    }
}
