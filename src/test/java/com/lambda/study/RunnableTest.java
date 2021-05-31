package com.lambda.study;

import java.util.function.Function;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-30
 * @Description
 */
public class RunnableTest {

    void run() {
        Function<Integer, Integer> function = input -> input + 1;
        function.apply(1);
    }

}
