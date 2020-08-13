package com.guava.study;

import java.io.IOException;
import java.util.Properties;

import org.apache.curator.shaded.com.google.common.io.Resources;
import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-11
 * @Description
 */
public class ResourcesStudy {

    @Test
    public void test() throws IOException {
        Properties properties = new Properties();
        properties.load(Resources.getResource("spring.properties").openStream());
        properties.forEach((k, v) -> System.out.println("K = " + k + " V = " + v));

        System.out.println(properties.getProperty("jdbc.password") instanceof String);
    }

}
