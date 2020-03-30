package com.base;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-27
 * @Description
 */
public class DateStudy {

    @Test
    public void test() {
        System.out.println(LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
    }

}
