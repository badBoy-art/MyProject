package com.faker;

import com.github.javafaker.Faker;
import junit.framework.TestCase;

/**
 * 生成随机数
 *
 * @author xuedui.zhao
 * @create 2019-08-01
 * @see <a href="https://github.com/rieckpil/blog-tutorials/tree/master/random-data-in-java-using-java-faker"/>
 */
public class study extends TestCase {

    public void testFaker() {
        Faker faker = new Faker();
        System.out.println(faker.name().firstName());
        System.out.println(faker.address().city());
    }
}
