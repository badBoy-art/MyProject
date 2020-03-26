package com.guava.study;

import com.google.common.math.IntMath;
import org.junit.Test;

import java.math.RoundingMode;

/**
 * @author xuedui.zhao
 * @create 2019-07-27
 */
public class MathStudy {

    @Test
    public void testMath() {
        System.out.println(IntMath.sqrt(3, RoundingMode.UP));
        System.out.println(IntMath.gcd(2, 4));
    }
}
