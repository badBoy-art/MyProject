package com.date.study;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xuedui.zhao
 * @create 2018-08-22
 */
public class study {

    @Test
    public void test01() {
        System.out.println(DateTime.now().plusDays(Days.ONE.getDays()).withTimeAtStartOfDay().toDate());
    }

    @Test
    public void test02() {
        System.out.println(DateUtils.addYears(new Date(), -1));
    }

    @Test
    public void test03() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = simpleDateFormat.parse("0:08:30");

        System.out.println(date);

    }

    @Test
    public void test04() {
        Date date = null;
        System.out.println(new Date().before(date));
    }

    /**
     * 解决时间格式化线程安全问题方案：
     * 1、parse方法加锁synchronized
     * 2、jdk8 DateTimeFormatter
     * 3、ThreadLocal<DateFormat>
     *
     * @throws Exception
     */
    @Test
    public void testSimpleNotThreadSafe() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 20; i++) {
            service.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(parse("2018-01-02 09:45:59"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // 等待上述的线程执行完
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parse(String strDate) throws ParseException {
        return sdf.parse(strDate);
    }

    @Test
    public void testJodaTime() {
        DateTime dateTime = new DateTime();
        System.out.println(dateTime);
    }

    @Test
    public void testBefore() {
        Date startDate = DateUtils.addDays(new Date(), 365);
        System.out.println(startDate);
        System.out.println(new Date().before(startDate));
        List<Date> dates = Lists.newArrayList();
        dates.add(new Date());
        dates.add(DateUtils.addDays(new Date(), 365));

        Collections.sort(dates);
        System.out.println(dates);
    }

    @Test
    public void testDate() {
        Date yesterday = DateUtils.addDays(new Date(), -1);

        /**
         * 默认获得前一天的数据
         */

        Date beginDate = DateUtils.setHours(yesterday, 0);
        beginDate = DateUtils.setMinutes(beginDate, 0);
        beginDate = DateUtils.setSeconds(beginDate, 0);

        Date endDate = DateUtils.setHours(yesterday, 23);
        endDate = DateUtils.setMinutes(endDate, 59);
        endDate = DateUtils.setSeconds(endDate, 59);
        System.out.println(beginDate);
        System.out.println(endDate);
    }

    @Test
    public void testAfter() {
        Date date = new Date();
        System.out.println(date.after(date));
    }

    @Test
    public void test() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.isBefore(LocalDateTime.of(LocalDate.of(2020, 03, 31), LocalTime.of(3, 59, 59))));
    }

}
