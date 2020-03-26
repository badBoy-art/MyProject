package com.util;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

import com.example.demo.jackson.Car;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-02-10
 * @Description
 */
public class UtilTest {

    @Test
    public void testRandomDataUtil() {
        List<Car> cars = Lists.newArrayList();
        Car car0 = new Car();
        car0.setBrand("BMW");
        Car car1 = new Car();
        car1.setBrand("toyota");
        Car car2 = new Car();
        car2.setBrand("honda");

        cars.add(car0);
        cars.add(car1);
        cars.add(car2);

        System.out.println(RandomDataUtil.generateRandomDataNoRepeat(cars, 2));
    }
}
