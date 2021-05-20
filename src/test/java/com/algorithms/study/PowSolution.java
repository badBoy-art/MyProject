package com.algorithms.study;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-11
 * @Description
 */
public class PowSolution {

    @Test
    public void test() {
        System.out.println(myPow(-2, -3));
    }

    private double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        } else if (n < 0) {
            return 1 / (x * myPow(x, -n - 1));
        } else if (n % 2 == 0) {
            return myPow(x * x, n / 2);
        } else {
            return x * myPow(x, n - 1);
        }
    }
}
