package com.my.study;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;

import java.net.InetAddress;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DateFormat {

    private static final String dataPattern = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void testDateUtils() {
        try {
            Date date = DateUtils.parseDate("2018-02-12 22:00:00", dataPattern);
            FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd");
            Date date1 = fastDateFormat.parse("2018-02-15");
            System.out.print(date);
            System.out.print(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {

        InetAddress addr = null;
        String address = "";
        try {
            addr = InetAddress.getLocalHost();//新建一个InetAddress类
            address = addr.getHostName();// 获得本机名称
            List<String> lists = Splitter.on(".").splitToList(address);
            for (String li : lists) {
                String theDigits = CharMatcher.JAVA_DIGIT.retainFrom(li);
                System.out.println(Long.getLong(theDigits));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(address);

    }

    @Test
    public void testNum() {
        List<String> lists = Splitter.on(".").splitToList("l-einvoice16.f.cn1");
        for (String li : lists) {
            String theDigits = CharMatcher.JAVA_DIGIT.retainFrom(li);
            System.out.println(theDigits);
        }
    }

    @Test
    public void testSortNature() {
        Date date1 = new Date();
        Date date2 = DateUtils.addHours(date1, 1);

        List<Date> lists = Lists.newArrayList();

        lists.add(date2);
        //lists.add(date1);

        //List<Date> list2 = Ordering.natural().sortedCopy(lists);
        Collections.sort(lists, (data1, data2) -> data1.compareTo(data2));
        System.out.println(lists);
        // System.out.println(list2.get(0));
        // Date date = DateUtils.addDays(list2.get(0),7);
        // System.out.println(date);

    }

    @Test
    public void testOrderingMin() {
        Date date1 = new Date();
        Date date2 = DateUtils.addHours(date1, 1);
        System.out.println(Ordering.natural().min(date1, date2));
        System.out.println(Ordering.natural().max(date1, date2));
    }
}
