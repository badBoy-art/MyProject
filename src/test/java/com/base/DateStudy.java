package com.base;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-27
 * @Description
 */
public class DateStudy {

    @Test
    public void test() {
        LocalDate localDate = LocalDate.now();

        System.out.println(localDate);
        System.out.println(localDate.minusDays(1));

        System.out.println("LocalDate 转 Long: " + localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    @Test
    public void testL() {
        Instant instant = Instant.ofEpochSecond(1585798418);
        ZoneId zone = ZoneId.systemDefault();
        System.out.println(LocalDateTime.ofInstant(instant, zone));
    }

}
