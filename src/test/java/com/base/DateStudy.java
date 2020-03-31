package com.base;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-27
 * @Description
 */
public class DateStudy {

    @Test
    public void test() {
        LocalDate localDate = LocalDate.now().plusDays(2);

        System.out.println("LocalDate 转 Long: " + localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

}
