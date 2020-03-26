package com.bigdecimal;

import com.reflect.study.Student;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xuedui.zhao
 * @create 2018-09-20
 */
public class DecimalTest {

    @Test
    public void testAdd() {
        BigDecimal bigDecimal01 = new BigDecimal(100);
        BigDecimal bigDecimal02 = new BigDecimal(200);
        System.out.println(bigDecimal01.compareTo(new BigDecimal(101)));
        bigDecimal01 = bigDecimal01.add(bigDecimal02);
        System.out.println(bigDecimal01);
        System.out.println(bigDecimal01.add(new BigDecimal(300)));
    }

    @Test
    public void test() {
        Integer integer = new Integer(10);
        System.out.println(Integer.compare(integer, 11) <= 0);
    }

    @Test
    public void testNull() {
        Student student = new Student();
        student.setAge(null);
        if (student.getAge() == null) {
            System.out.println("true");
        }
    }

    @Test
    public void test02() {
        BigDecimal bigDecimal = new BigDecimal("20");
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println(bigDecimal);
    }
}
