package com.guava.study;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

/**
 * @author xuedui.zhao
 * @create 2019-07-26
 */
public class OrderingStudy {

    @Test
    public void testOrdering() {
        Ordering<String> lengthOrdering = new Ordering<String>() {
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        List<String> list = Lists.newArrayList("a", "bc", "cde");
        List<String> listCopy = lengthOrdering.reverse().sortedCopy(list);
        System.out.println(listCopy);
        System.out.println(lengthOrdering.sortedCopy(listCopy));
    }

    @Test
    public void testMyOrdering() {
        // 按最后更新时间排序，如果最后更新时间相同，按地址排序（把有地址的排在前面）
        Ordering<Date> ordering = Ordering.from(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.after(o2) ? 1 : -1;
            }
        });
        List<Date> dates = Lists.newArrayListWithCapacity(2);
        Date date = new Date();
        dates.add(date);
        Date date2 = DateUtils.addDays(date, 2);
        dates.add(date2);

        System.out.println(ordering.max(dates));
    }

    @Test
    public void testEmptyList() {

        List<String> strs = Lists.newArrayList();
        System.out.println(strs.iterator().next());
        String str = Ordering.natural().max(strs);
        System.out.println(str);
    }

}
