package com.base;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import net.sf.cglib.core.Local;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-27
 * @Description
 */
public class DateStudy {

    @Test
    public void test() {
        LocalDate localDate = LocalDate.now();

        System.out.println(LocalDate.now().minusDays(1));
        System.out.println(localDate.minusDays(1));
        System.out.println(localDate.minusDays(1).minusWeeks(1).with(DayOfWeek.SUNDAY));

        System.out.println("LocalDate 转 Long: " + localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(localDate.equals(LocalDate.of(2020,04,13)));
    }

    @Test
    public void testL() {
        Instant instant = Instant.ofEpochSecond(1586707199);
        ZoneId zone = ZoneId.systemDefault();
        System.out.println(LocalDateTime.ofInstant(instant, zone));
        System.out.println(Instant.ofEpochSecond(1586707199).atZone(ZoneOffset.ofHours(8)).toLocalDate());
    }

    @Test
    public void testSysTimes() throws InterruptedException {
        long beginTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            System.currentTimeMillis();
        }
        long elapsedTime = System.nanoTime() - beginTime;
        System.out.println("100 System.currentTimeMillis() serial calls: " + elapsedTime + " ns");

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    System.currentTimeMillis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        beginTime = System.nanoTime();
        startLatch.countDown();
        endLatch.await();
        elapsedTime = System.nanoTime() - beginTime;

        System.out.println("100 System.currentTimeMillis() parallel calls: " + elapsedTime + " ns");
    }

}
